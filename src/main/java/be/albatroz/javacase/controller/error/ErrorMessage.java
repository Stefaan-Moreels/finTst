package be.albatroz.javacase.controller.error;

import be.albatroz.javacase.JavaCaseApplication;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class ErrorMessage {

    private Integer status;
    private String error;
    private Map<String, ValError> validationErrors;
    private String exception;
    private String message;
    private long timestamp;
    private String path;

    public ErrorMessage(HttpStatus status, Exception e, HttpServletRequest request) {


        setTimestamp(System.nanoTime());
        setPath(request.getRequestURI());

        setStatus(status.value());
        setError(status.getReasonPhrase());

        setException(e.getClass().getSimpleName().replace("Exception", ""));

        if (e instanceof ViolationsException) {
            Set<? extends ConstraintViolation<?>> constraintViolations = ((ViolationsException) e).getViolations();
            this.validationErrors = new HashMap<>();

            constraintViolations.forEach(constraintViolation -> {
                String key = constraintViolation.getPropertyPath().toString();
                ValError valError = new ValError(key, key, constraintViolation.getMessage());

                this.validationErrors.put(key, valError);
            });

            return;
        }

//        if (e instanceof MethodArgumentNotValidException) {
//            Set<? extends ConstraintViolation<?>> constraintViolations = ((MethodArgumentNotValidException) e).getAllErrors();
//            this.validationErrors = new HashMap<>();
//
//            constraintViolations.forEach(constraintViolation -> {
//                String key = constraintViolation.getPropertyPath().toString();
//                ValError valError = new ValError(key, key, constraintViolation.getMessage());
//
//                this.validationErrors.put(key, valError);
//            });
//
//            return;
//        }

        if (e instanceof MethodArgumentNotValidException) {
            validationErrors = ((MethodArgumentNotValidException) e)
                    .getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(ValError::new)
                    .collect(Collectors.toMap(ValError::getField, Function.identity()));
        } else {
            setMessage(e.getMessage());
        }
    }

    @Value
    class ValError {

        private String field;
        private Object value;
        private String message;

        ValError(ObjectError error) {


            if (error instanceof FieldError) {
                this.field = ((FieldError) error).getField();
                this.value = ((FieldError) error).getRejectedValue();
            } else {
                this.field = null;
                this.value = null;
            }

            final MessageSource messageSource = JavaCaseApplication.getApplicationContext().getBean(MessageSource.class);

            Optional<String> msg = Optional.empty();
            for (String code : Objects.requireNonNull(error.getCodes())) {
                try {
                    msg = Optional.of(messageSource.getMessage(code, null, Locale.ENGLISH));
                } catch (NoSuchMessageException e) {
                    // do nothing and try next code
                }

                // code found
                if (msg.isPresent()) {
                    break;
                }
            }
            this.message = msg.orElse(error.getDefaultMessage());
        }

        ValError(String field, Object value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }
    }


}

package be.albatroz.javacase.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ViolationsException extends ConstraintViolationException {
    private final Set<? extends ConstraintViolation<?>> violations;
    private final Object subject;

    public ViolationsException(Object object, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
        subject = object;
        violations = constraintViolations;
    }

    public Set<? extends ConstraintViolation<?>> getViolations() {
        return violations;
    }

    public Object getSubject() {
        return subject;
    }
}

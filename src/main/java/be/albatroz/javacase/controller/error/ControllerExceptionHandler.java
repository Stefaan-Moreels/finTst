package be.albatroz.javacase.controller.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.util.NestedServletException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * A generic exception handler that translates the exceptions thrown by the controller layer, in ErrorMessage POJO's
 * for REST serialization towards the front-end
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private void errorLog(Exception e, HttpServletRequest request) {
        log.error("{} => {}: {}", request.getRequestURL(), e.getClass().getSimpleName(), e.getMessage());
        if (!(e instanceof ClientAbortException)) {
            log.warn(e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler({IOException.class, ClientAbortException.class})
    @ResponseBody
    public ErrorMessage ioException(Exception e, HttpServletRequest request) {
        errorLog(e, request);
        return null;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            FileNotFoundException.class,
            IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class,
            ConversionFailedException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            UnsupportedOperationException.class,
            AssertionError.class,
            NestedServletException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(Exception e, HttpServletRequest request) {
        errorLog(e, request);
        return new ErrorMessage(HttpStatus.BAD_REQUEST, e, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class,
            EmptyResultDataAccessException.class})
    @ResponseBody
    public ErrorMessage notFound(Exception e, HttpServletRequest request) {
        errorLog(e, request);
        return new ErrorMessage(HttpStatus.NOT_FOUND, e, request);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            ViolationsException.class,
            ExecutionException.class,
            InterruptedException.class
    })
    @ResponseBody
    public ErrorMessage unprocessableEntity(ViolationsException e, HttpServletRequest request) {
        errorLog(e, request);
        return new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, e, request);
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler({
            DataAccessResourceFailureException.class
    })
    @ResponseBody
    public ErrorMessage requestTimeout(DataAccessResourceFailureException e, HttpServletRequest request) {
        errorLog(e, request);
        return new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, e, request);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            NullPointerException.class,
            Exception.class,
            RuntimeException.class,
            IllegalStateException.class
    })
    @ResponseBody
    public ErrorMessage internalServerError(Exception e, HttpServletRequest request) {
        errorLog(e, request);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

}

package com.bol.assignment.exception;

import com.bol.assignment.msg.ErrorMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception Handler class for creating proper message and log.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {
    /**
     * Handles all errors that are in the form of a ServiceException.class
     *
     * @param exception the cause
     * @return an instance of ErrorMsg with an appropriate status code
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorMsg> handleServiceException(ServiceException exception) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        if (exception.getErrorCode().isPresent()) {
            httpStatus = exception.getErrorCode().get().getValue();
        }
        String format = String.format(exception.getErrorCode().get().getMessage() + ",data: %s", exception.getAdditionalData());
        log.error(format);
        return new ResponseEntity<>(new ErrorMsg(httpStatus.value(), exception.getMessage()), httpStatus);
    }

    /**
     * Handles all errors that are in the form of a Exception.class
     *
     * @param exception the cause
     * @return an instance of ErrorMsg with an appropriate status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> handleOtherExceptions(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorMsg(httpStatus.value(), exception.getMessage()), httpStatus);
    }
}

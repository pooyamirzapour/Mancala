package com.bol.assignment.exception;

import java.util.Optional;

/**
 * A custom exception.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
public class ServiceException extends RuntimeException {

    private final Optional<ErrorCode> errorCode;
    private final String additionalData;


    /**
     * Constructs a new exception with the specified error code and detail message.
     *
     * @param errorCode      the error code
     * @param additionalData the detail message for log
     */
    public ServiceException(ErrorCode errorCode, String additionalData) {
        super(errorCode.getMessage());
        this.errorCode = Optional.ofNullable(errorCode);
        this.additionalData = additionalData;
    }

    public Optional<ErrorCode> getErrorCode() {
        return errorCode;
    }

    public String getAdditionalData() {
        return additionalData;
    }
}

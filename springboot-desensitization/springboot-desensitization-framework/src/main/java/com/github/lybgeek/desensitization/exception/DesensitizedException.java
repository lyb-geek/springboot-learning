package com.github.lybgeek.desensitization.exception;


public class DesensitizedException extends RuntimeException{

    public DesensitizedException() {
    }

    public DesensitizedException(String message) {
        super(message);
    }

    public DesensitizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DesensitizedException(Throwable cause) {
        super(cause);
    }

    public DesensitizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.github.lybgeek.config.auth.exception;


public class AuthException extends RuntimeException{

    private final String message;

    private final String errorCode;

    public AuthException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public String getErrorCode() {
        return errorCode;
    }


}

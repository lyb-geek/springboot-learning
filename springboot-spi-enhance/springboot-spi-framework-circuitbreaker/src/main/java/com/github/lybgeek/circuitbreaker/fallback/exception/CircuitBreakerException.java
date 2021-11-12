package com.github.lybgeek.circuitbreaker.fallback.exception;


public class CircuitBreakerException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected final String message;

    protected Integer errorCode;

    public CircuitBreakerException(String message) {
        this.message = message;
    }

    public CircuitBreakerException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public CircuitBreakerException(Integer errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }
    public CircuitBreakerException(Integer errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}

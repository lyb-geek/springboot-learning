package com.github.lybgeek.exception;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected final String message;
    protected Integer errorCode;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public BizException(Integer errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }

    public BizException(Integer errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}

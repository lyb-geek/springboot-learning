package com.github.lybgeek.license.exception;


public class LicenseException extends RuntimeException{

    protected final String message;

    protected Integer errorCode;

    public LicenseException(String message) {
        this.message = message;
    }

    public LicenseException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }
    public LicenseException(Integer errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }
    public LicenseException(Integer errorCode, String message) {
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

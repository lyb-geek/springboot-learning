package com.github.lybgeek.mq.exception;


public class MqExecption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected final String message;

    protected String errorCode;

    public MqExecption(String message) {
        this.message = message;
    }

    public MqExecption(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }
    public MqExecption(String errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }
    public MqExecption(String errorCode, String message) {
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

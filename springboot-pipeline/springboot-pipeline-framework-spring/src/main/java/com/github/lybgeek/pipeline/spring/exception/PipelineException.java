package com.github.lybgeek.pipeline.spring.exception;


public class PipelineException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected final String message;

    protected Integer errorCode;

    public PipelineException(String message) {
        this.message = message;
    }

    public PipelineException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }
    public PipelineException(Integer errorCode, String message, Throwable e) {
        super(message, e);
        this.message = message;
        this.errorCode = errorCode;
    }
    public PipelineException(Integer errorCode, String message) {
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

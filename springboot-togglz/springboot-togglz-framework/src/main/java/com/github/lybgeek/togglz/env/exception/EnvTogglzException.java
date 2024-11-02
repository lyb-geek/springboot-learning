package com.github.lybgeek.togglz.env.exception;


public class EnvTogglzException extends RuntimeException{

    private int code;
    private String message;

    public EnvTogglzException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.github.lybgeek.swagger.test.user.exception;


public class UserException extends RuntimeException{
    private String message;
    private Integer code;

    public UserException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}

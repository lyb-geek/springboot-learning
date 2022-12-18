package com.github.lybgeek.http.common.exception;


public class HttpException extends RuntimeException{

    public HttpException() {
    }

    public HttpException(String message) {
        super(message);
    }
}

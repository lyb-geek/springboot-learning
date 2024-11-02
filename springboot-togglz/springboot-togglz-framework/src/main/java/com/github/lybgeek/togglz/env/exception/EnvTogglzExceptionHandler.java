package com.github.lybgeek.togglz.env.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EnvTogglzExceptionHandler {


    @ExceptionHandler(EnvTogglzException.class)
    public ResponseEntity<String> handleEnvTogglzException(EnvTogglzException e){
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}

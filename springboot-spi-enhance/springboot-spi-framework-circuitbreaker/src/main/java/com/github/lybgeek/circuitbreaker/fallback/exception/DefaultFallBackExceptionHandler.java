package com.github.lybgeek.circuitbreaker.fallback.exception;


import com.github.lybgeek.circuitbreaker.fallback.model.DefaultFallBackResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultFallBackExceptionHandler {


    @ExceptionHandler(CircuitBreakerException.class)
    public DefaultFallBackResponse handleBlockException(CircuitBreakerException e) {
       return DefaultFallBackResponse.builder().msg(e.getMessage()).code(e.getErrorCode()).build();
    }

}

package com.github.lybgeek.config.auth.exception.advice;

import com.github.lybgeek.config.auth.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(-1)
public class GloabalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handle(Exception e){
        String message = "系统异常，请联系管理员";
        if(e.getMessage()!=null){
            message = e.getMessage();
        }
        log.error(e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handle(AuthException e){
        String message = "您当前没有访问该地址的权限，请联系管理员";
        if(e.getMessage()!=null){
            message = e.getMessage();
        }
        log.error("AUth fail:" + message,e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}

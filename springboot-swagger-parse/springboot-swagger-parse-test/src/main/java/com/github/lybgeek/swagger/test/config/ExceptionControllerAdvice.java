package com.github.lybgeek.swagger.test.config;


import com.github.lybgeek.swagger.test.dto.RPCResult;
import com.github.lybgeek.swagger.test.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {



    @ExceptionHandler(Exception.class)
    public RPCResult handleException(Exception e){
        log.error("系统异常",e);
        return RPCResult.error("系统异常:" + e.getMessage(),500);
    }


    @ExceptionHandler(UserException.class)
    public RPCResult handleUserException(UserException e){
        log.error("用户异常",e);
        return RPCResult.error(e.getMessage(),e.getCode());
    }
}

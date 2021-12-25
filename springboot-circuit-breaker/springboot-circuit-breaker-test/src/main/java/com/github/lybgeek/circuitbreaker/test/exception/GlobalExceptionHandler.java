package com.github.lybgeek.circuitbreaker.test.exception;


import com.github.lybgeek.circuitbreaker.framework.ciruitbreaker.model.AjaxResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler
    public AjaxResult exception (Exception ex){
      return AjaxResult.error(ex.getMessage(),500);
    }

    @ExceptionHandler
    public AjaxResult exception (BizException ex){
        return AjaxResult.error(ex.getMessage(),ex.getErrorCode());
    }
}

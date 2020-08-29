package com.github.lybgeek.common.advice;


import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestControllerAdvice(basePackages = "com.github.lybgeek")
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(Objects.isNull(o)){
            return Result.builder().message("success").build();
        }

        if(o instanceof Result){
            return o;
        }

        return Result.builder().message("success").data(o).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return Result.builder().message(e.getMessage()).status(Result.fail).build();
    }

    /**
     * 针对业务异常统一处理
     * @param request
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
    public Result<?> bizExceptionHandler(HttpServletRequest request, BizException bizException) {
            int errorCode = bizException.getCode();
            log.error("catch bizException {}", errorCode);
            return Result.builder().message(bizException.getMessage()).status(errorCode).build();
    }


    /**
     * 针对Validate校验异常统一处理
     * @param request
     * @param methodArgumentNotValidException
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Result<?> methodArgumentNotValidExceptionExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException methodArgumentNotValidException) {
        Result result = new Result();
        log.error("catch methodArgumentNotValidException :" + methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        return ResultUtils.INSTANCE.getFailResult(methodArgumentNotValidException.getBindingResult(),result);
    }

    /**
     * 针对Assert断言异常统一处理
     * @param request
     * @param illegalArgumentExceptionException
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
    public Result<?> illegalArgumentExceptionHandler(HttpServletRequest request, IllegalArgumentException illegalArgumentExceptionException) {
        log.error("illegalArgumentExceptionException:"+illegalArgumentExceptionException.getMessage(), illegalArgumentExceptionException);
        return Result.builder().message(illegalArgumentExceptionException.getMessage()).status(Result.fail).build();
    }



}

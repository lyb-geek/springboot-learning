package com.github.lybgeek.common.config;

import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.Result;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class ContextBinderConfig implements ResponseBodyAdvice {



    @ExceptionHandler
    @ResponseBody
    public <T> Result<T> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return new Result<>().setErrorMsgInfo(e.getMessage());
    }



    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return o;
    }
}

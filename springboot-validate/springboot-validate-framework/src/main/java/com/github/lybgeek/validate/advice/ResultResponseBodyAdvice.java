package com.github.lybgeek.validate.advice;


import com.github.lybgeek.validate.model.AjaxResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.github.lybgeek")
@RequiredArgsConstructor
@Slf4j
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final MessageSource messageSource;


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult validationException(MethodArgumentNotValidException exception){
        Map<String,String> errorMap = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        log.error("validate error:{}",exception.getMessage());
        String message = messageSource.getMessage("message.validate.error",null,"validate error", LocaleContextHolder.getLocale());
        return AjaxResult.fail(message,String.valueOf(HttpStatus.BAD_REQUEST.value()),errorMap);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public AjaxResult exception(Exception exception){
        log.error("exception:{}",exception.getMessage());
        String message = messageSource.getMessage("message.exception.error",null,"exception error", LocaleContextHolder.getLocale());
        return AjaxResult.fail(message,String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage());

    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof AjaxResult){
            return body;
        }
        return AjaxResult.success(body);
    }
}

package com.github.lybgeek.jsonview.advice;


import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lybgeek.jsonview.factory.JsonViewFactory;
import com.github.lybgeek.jsonview.property.JsonViewProperty;
import com.github.lybgeek.jsonview.support.PublicJsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

import static com.github.lybgeek.jsonview.factory.JsonViewFactory.JSON_VIEW_FACTORY_SUFFIX;

@RestControllerAdvice
@Slf4j
public class JsonViewReponseBodyAdvice implements ResponseBodyAdvice<Object>, ApplicationContextAware {

    private final ObjectMapper objectMapper;

    private final JsonViewProperty jsonViewProperty;

    private ApplicationContext applicationContext;

    public JsonViewReponseBodyAdvice(JsonViewProperty jsonViewProperty) {
        this.jsonViewProperty = jsonViewProperty;
        objectMapper = new ObjectMapper();

    }
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if(!jsonViewProperty.isEnabled() || returnType.hasMethodAnnotation(JsonView.class) || CollectionUtils.isEmpty(jsonViewProperty.getJsonViewModelClasses())){
            return false;
        }

        return isMatchJsonViewModel(Objects.requireNonNull(returnType.getMethod()).getReturnType());

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body == null){
            return null;
        }
        Class<? extends PublicJsonView> jsonViewClass = getJsonViewClass(body,request);

        try {
            if(jsonViewClass != null){
                objectMapper.setConfig(objectMapper.getSerializationConfig().withView(jsonViewClass));
            }
            String json = objectMapper.writeValueAsString(body);
            return objectMapper.readValue(json,body.getClass());
        } catch (Exception e) {
            log.error("json序列化异常",e);
        }finally {
            // 重置ObjectMapper的JsonView配置
            objectMapper.setConfig(objectMapper.getSerializationConfig().withView(null));
        }
        return body;
    }

    private Class<? extends PublicJsonView> getJsonViewClass(Object body,ServerHttpRequest request) {
        if(body == null || !isMatchJsonViewModel(body.getClass())){
            return null;
        }

        JsonViewFactory jsonViewFactory = getJsonViewFactory(body);

        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;

        return jsonViewFactory.getJsonViewClass(servletServerHttpRequest.getServletRequest(),body);


    }

    private JsonViewFactory getJsonViewFactory(Object body){
        if(body == null){
            return null;
        }

        return applicationContext.getBean(body.getClass().getName() + JSON_VIEW_FACTORY_SUFFIX,JsonViewFactory.class);

    }


    private boolean isMatchJsonViewModel(Class<?> modelClz){
        return jsonViewProperty.getJsonViewModelClasses().stream().anyMatch(clazz -> clazz.equals(modelClz));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

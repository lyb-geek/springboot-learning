package com.github.lybgeek.common.swagger.version.config;


import com.github.lybgeek.common.swagger.version.annotation.ApiVersion;
import com.github.lybgeek.common.swagger.version.util.ApiVersionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        // 扫描类上的 @ApiVersion
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createRequestCondition(apiVersion);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        // 扫描方法上的 @ApiVersion
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createRequestCondition(apiVersion);
    }
 
    private RequestCondition<ApiVersionCondition> createRequestCondition(ApiVersion apiVersion) {

        if (Objects.isNull(apiVersion) || !apiVersion.useVersion()) {
            return null;
        }

        String version = ApiVersionUtil.INSTANCE.getApiVersion(apiVersion);
        return new ApiVersionCondition(version);
    }



}

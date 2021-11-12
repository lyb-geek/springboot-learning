package com.github.lybgeek.circuitbreaker.spi.proxy.model;


import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.circuitbreaker.annotation.CircuitBreakerActivate;
import com.github.lybgeek.circuitbreaker.spi.proxy.util.ClassUtils;
import com.github.lybgeek.interceptor.model.Invocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class CircuitBreakerInvocation extends Invocation {


    public CircuitBreakerInvocation(Object target, Method method, Object[] args) {
        super(target, method, args);
    }

    public CircuitBreakerFallback getCircuitBreakerFallback() {
        CircuitBreakerFallback circuitBreakerFallback = CircuitBreakerFallback.builder().fallback(void.class).fallbackFactory(void.class).build();
        Class targetClz = ClassUtils.getClassType(getTarget());
        CircuitBreakerActivate circuitBreakerActivate = AnnotationUtils.findAnnotation(targetClz,CircuitBreakerActivate.class);
        if(ObjectUtil.isNotNull(circuitBreakerActivate)){
            circuitBreakerFallback.setFallback(circuitBreakerActivate.fallback());
            circuitBreakerFallback.setFallbackFactory(circuitBreakerActivate.fallbackFactory());
        }

        return circuitBreakerFallback;
    }
}

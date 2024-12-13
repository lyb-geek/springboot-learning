package com.github.lybgeek.plugin.spring.aop.pointcut;


import com.github.lybgeek.plugin.spring.aop.annotation.MethodCostTime;
import org.pf4j.Extension;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

public class MethodCostTimePointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return AnnotatedElementUtils.hasAnnotation(method, MethodCostTime.class) && AnnotatedElementUtils.hasAnnotation(targetClass, Extension.class);
    }
}

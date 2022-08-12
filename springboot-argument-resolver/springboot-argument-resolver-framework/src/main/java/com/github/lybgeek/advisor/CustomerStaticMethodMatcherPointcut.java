package com.github.lybgeek.advisor;


import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

public class CustomerStaticMethodMatcherPointcut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
       return AnnotatedElementUtils.hasAnnotation(targetClass, Controller.class);
    }
}

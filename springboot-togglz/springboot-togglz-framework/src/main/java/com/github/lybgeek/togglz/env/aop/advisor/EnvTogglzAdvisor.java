package com.github.lybgeek.togglz.env.aop.advisor;


import com.github.lybgeek.togglz.env.annotation.EnvTogglz;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

public class EnvTogglzAdvisor extends StaticMethodMatcherPointcutAdvisor {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return AnnotatedElementUtils.hasAnnotation(method, EnvTogglz.class);
    }
}

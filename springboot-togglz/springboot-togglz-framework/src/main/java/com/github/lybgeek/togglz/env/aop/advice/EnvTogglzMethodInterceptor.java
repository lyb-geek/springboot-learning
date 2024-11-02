package com.github.lybgeek.togglz.env.aop.advice;


import com.github.lybgeek.togglz.env.annotation.EnvTogglz;
import com.github.lybgeek.togglz.env.exception.EnvTogglzException;
import com.github.lybgeek.togglz.env.feature.EnvFeature;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class EnvTogglzMethodInterceptor implements MethodInterceptor {

    private final Environment environment;
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        EnvTogglz envTogglz = AnnotatedElementUtils.findMergedAnnotation(invocation.getMethod(), EnvTogglz.class);
        if(envTogglz == null){
            return invocation.proceed();
        }
        String activeEnv = environment.resolvePlaceholders(envTogglz.activeEnv());

        if(EnvFeature.getEnvFeature(activeEnv).isActive()){
            return invocation.proceed();
        }

        throw new EnvTogglzException("非法访问",HttpStatus.FORBIDDEN.value());
    }
}

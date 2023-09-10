package com.github.lybgeek.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;

@Slf4j
public class LogMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        try {
            result = invocation.proceed();
        } finally {
           log.info(">>>>>>>>>>>>>>>>>>>>>>>>TargetClass:【{}】,method:【{}】,args:【{}】",invocation.getThis().getClass().getName(),invocation.getMethod().getName(), Arrays.toString(invocation.getArguments()));
        }

        return result;
    }
}

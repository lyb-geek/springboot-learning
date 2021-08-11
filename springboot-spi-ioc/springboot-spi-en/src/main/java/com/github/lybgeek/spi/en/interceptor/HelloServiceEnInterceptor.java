package com.github.lybgeek.spi.en.interceptor;


import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;
import com.github.lybgeek.spi.framework.plugin.model.Invocation;

import java.util.Arrays;

public class HelloServiceEnInterceptor implements Interceptor {

    @Override
    public boolean preHandle(Invocation invocation) {
        System.out.println("HelloServiceEnInterceptor-->preHandle:"+invocation.getMethod().getName() + ";args:" + Arrays.asList(invocation.getArgs()));
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println("HelloServiceEnInterceptor-->afterCompletion:"+invocation.getMethod().getName() + ";args:" + Arrays.asList(invocation.getArgs()));
    }

    @Override
    public int getOrder() {
        return Interceptor.HIGHEST_PRECEDENCE;
    }
}

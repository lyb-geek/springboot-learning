package com.github.lybgeek.spi.cn.interceptor;


import com.github.lybgeek.spi.framework.plugin.interceptor.Interceptor;
import com.github.lybgeek.spi.framework.plugin.model.Invocation;

import java.util.Arrays;

public class HelloServiceCnInterceptor implements Interceptor {

    @Override
    public boolean preHandle(Invocation invocation) {
        System.out.println("HelloServiceCnInterceptor-->preHandle:"+invocation.getMethod().getName() + ";args:" + Arrays.asList(invocation.getArgs()));
        return true;
    }

    @Override
    public void afterCompletion(Invocation invocation) {
        System.out.println("HelloServiceCnInterceptor-->afterCompletion:"+invocation.getMethod().getName() + ";args:" + Arrays.asList(invocation.getArgs()));
    }

    @Override
    public int getOrder() {
        return Interceptor.HIGHEST_PRECEDENCE;
    }
}

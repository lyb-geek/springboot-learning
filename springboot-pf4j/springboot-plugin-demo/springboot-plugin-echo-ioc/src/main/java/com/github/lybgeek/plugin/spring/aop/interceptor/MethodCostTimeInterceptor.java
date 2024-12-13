package com.github.lybgeek.plugin.spring.aop.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MethodCostTimeInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
            long start = System.currentTimeMillis();
        Object result = null;
        try {
            result = invocation.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            long end = System.currentTimeMillis();
            System.out.println("method:"+invocation.getMethod().getName()+" cost time:"+(end-start)+"ms");
        }

            return result;
    }
}

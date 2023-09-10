package com.github.lybgeek.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeCostMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result;
        long startTime = System.currentTimeMillis();
        try {
            result = invocation.proceed();
        } finally {
           log.info(">>>>>>>>>>>>>>>>>>>>>>>>{} costTime:【{}】 ms",invocation.getMethod().getName(),(System.currentTimeMillis() - startTime));
        }

        return result;
    }
}

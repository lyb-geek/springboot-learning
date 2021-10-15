package com.github.lybgeek.spi.test.interceptor;


import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;


@Intercepts(@Signature(type = HelloServiceA.class,method = "hello",args = {}))
public class InterceptorA implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("InterceptorA ....");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }


}

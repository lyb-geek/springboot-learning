package com.github.lybgeek.spi.test.interceptor;


import com.github.lybgeek.interceptor.Interceptor;
import com.github.lybgeek.interceptor.annotation.Intercepts;
import com.github.lybgeek.interceptor.annotation.Signature;
import com.github.lybgeek.interceptor.model.Invocation;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;
import com.github.lybgeek.spi.test.interceptor.service.HelloServiceB;


@Intercepts({@Signature(type = HelloServiceA.class,method = "hello",args = {}),
@Signature(type = HelloServiceB.class,method = "hi",args = {String.class}),
        @Signature(type = HelloServiceB.class,method = "hi",args = {}),
        @Signature(type = HelloServiceA.class,method = "hello",args = {String.class})})
public class InterceptorC implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("InterceptorC....");
        return invocation.proceed();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}

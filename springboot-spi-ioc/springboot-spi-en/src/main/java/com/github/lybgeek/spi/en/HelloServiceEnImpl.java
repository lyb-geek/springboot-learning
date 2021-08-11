package com.github.lybgeek.spi.en;


import com.github.lybgeek.spi.HelloService;
import com.github.lybgeek.spi.en.interceptor.HelloServiceEnInterceptor;
import com.github.lybgeek.spi.framework.plugin.anotation.InterceptorMethod;

public class HelloServiceEnImpl implements HelloService {


    @Override
    @InterceptorMethod(interceptorClasses = HelloServiceEnInterceptor.class)
    public String sayHello(String username) {
        return "hello：" + username;
    }
}

package com.github.lybgeek.spi.cn;


import com.github.lybgeek.spi.HelloService;
import com.github.lybgeek.spi.cn.interceptor.HelloServiceCnInterceptor;
import com.github.lybgeek.spi.cn.interceptor.HelloServiceCnOtherInterceptor;
import com.github.lybgeek.spi.framework.plugin.anotation.InterceptorMethod;

public class HelloServiceCnImpl implements HelloService {

    @Override
    @InterceptorMethod(interceptorClasses = {HelloServiceCnInterceptor.class, HelloServiceCnOtherInterceptor.class})
    public String sayHello(String username) {
        return "你好：" + username;
    }
}

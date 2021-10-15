package com.github.lybgeek.spi.test.interceptor.service.impl;

import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;


public class HelloServiceAImpl implements HelloServiceA {
    @Override
    public String hello() {
        return "hello-->A";
    }

    @Override
    public String hello(String username) {
        return "hello-->" + username;
    }
}

package com.github.lybgeek.spi.test.interceptor.service.impl;

import com.github.lybgeek.spi.test.interceptor.service.HelloServiceA;


public class HelloServiceCImpl implements HelloServiceA {
    @Override
    public String hello() {
        return "hello-->c";
    }

    @Override
    public String hello(String username) {
        return "hello-->c" + username;
    }
}

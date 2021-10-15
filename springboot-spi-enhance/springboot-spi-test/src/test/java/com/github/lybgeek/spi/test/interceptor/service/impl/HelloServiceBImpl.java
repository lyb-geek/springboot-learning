package com.github.lybgeek.spi.test.interceptor.service.impl;

import com.github.lybgeek.spi.test.interceptor.service.HelloServiceB;


public class HelloServiceBImpl implements HelloServiceB {
    @Override
    public String hi() {
        return "hi-->B";
    }

    @Override
    public String hi(String username) {
        return "hi-->" + username;
    }
}

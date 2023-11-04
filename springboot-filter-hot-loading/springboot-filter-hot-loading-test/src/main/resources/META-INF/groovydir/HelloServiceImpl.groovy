package com.github.lybgeek.service.impl;

import com.github.lybgeek.service.HelloService;


public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String username) {
        println ("hello:" + username)
        return "hello:" + username;
    }
}

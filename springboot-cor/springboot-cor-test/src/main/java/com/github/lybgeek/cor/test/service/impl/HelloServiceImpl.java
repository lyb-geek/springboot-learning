package com.github.lybgeek.cor.test.service.impl;


import com.github.lybgeek.cor.test.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String username) {
        return "hello : " + username;
    }
}

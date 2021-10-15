package com.github.lybgeek.spi.test.spring.service.impl;


import com.github.lybgeek.spi.test.spring.service.HelloService;
import org.springframework.stereotype.Service;

@Service("helloEn")
public class HelloEnServiceImpl implements HelloService {
    @Override
    public String sayHi(String username) {
        return "hello:" + username;
    }
}

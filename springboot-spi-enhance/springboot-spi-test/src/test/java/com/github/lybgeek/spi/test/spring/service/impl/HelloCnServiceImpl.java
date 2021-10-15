package com.github.lybgeek.spi.test.spring.service.impl;


import com.github.lybgeek.spi.test.spring.service.HelloService;
import org.springframework.stereotype.Service;

@Service("helloCn")
public class HelloCnServiceImpl implements HelloService {
    @Override
    public String sayHi(String username) {
        return "你好：" + username;
    }
}

package com.gitee.lybgeek.service.impl;

import com.gitee.lybgeek.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String username) {
        return "hello:" + username;
    }
}

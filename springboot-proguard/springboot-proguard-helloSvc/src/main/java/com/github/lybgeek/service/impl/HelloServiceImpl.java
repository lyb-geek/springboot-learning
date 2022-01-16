package com.github.lybgeek.service.impl;


import com.github.lybgeek.license.annotation.LicenseCheck;
import com.github.lybgeek.service.HelloService;

public class HelloServiceImpl implements HelloService {


    @Override
    @LicenseCheck
    public String hello(String username) {
        return "hello:" + username;
    }
}

package com.github.lybgeek.service;


import com.github.lybgeek.annotation.Log;
import com.github.lybgeek.model.HelloServiceProperties;

public class HelloService {

    private String beanName;

    private HelloServiceProperties helloServiceProperties;

    public HelloService(HelloServiceProperties helloServiceProperties) {
        this.helloServiceProperties = helloServiceProperties;
    }

    public HelloService(HelloServiceProperties helloServiceProperties,String beanName) {
        this.beanName = beanName;
        this.helloServiceProperties = helloServiceProperties;
    }

    @Log
    public String sayHi(String username){
        return "hi : " + username;
    }
}

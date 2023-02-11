package com.github.lybgeek.aop.test.service;


import com.github.lybgeek.aop.annotation.CostTimeRecoder;

import java.util.concurrent.TimeUnit;

public class HelloService {

    @CostTimeRecoder
    public String sayHello(String message){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello : " + message;
    }
}

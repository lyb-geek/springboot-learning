package com.github.lybgeek.test.service;


import com.github.lybgeek.apt.annotation.CostTimeRecoder;

import java.util.concurrent.TimeUnit;

public class HelloService {

    @CostTimeRecoder
    public String sayHello(String username){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello : " + username;
    }
}

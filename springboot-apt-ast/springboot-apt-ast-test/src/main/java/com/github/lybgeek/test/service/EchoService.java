package com.github.lybgeek.test.service;


import com.github.lybgeek.apt.annotation.CostTimeRecoder;

public class EchoService {

    @CostTimeRecoder
    public void echo(String message){
        System.out.println("echo-->" + message);
    }
}

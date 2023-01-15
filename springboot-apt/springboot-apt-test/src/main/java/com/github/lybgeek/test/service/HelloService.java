package com.github.lybgeek.test.service;


import com.github.lybgeek.apt.anntation.AutoComponent;

import javax.annotation.PostConstruct;

@AutoComponent
public class HelloService {

    public String hello(){
        return "hello apt";
    }


    @PostConstruct
    public void init(){
        System.out.println("来自远方的狼。。。");
    }
}

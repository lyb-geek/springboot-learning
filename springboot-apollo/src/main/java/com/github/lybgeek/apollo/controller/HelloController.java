package com.github.lybgeek.apollo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${hello}")
    private String hello;


    @GetMapping(value = "/hello")
    public String sayHello(){

        return "hello:"+hello;
    }
}

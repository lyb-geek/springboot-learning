package com.github.lybgeek.interceptor.test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("{username}")
    public String sayHello(@PathVariable("username") String username){
        return "hello:" + username;
    }
}

package com.github.lybgeek.app.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {


    @GetMapping("{username}")
    public String hello(@PathVariable("username") String username){

        return "hello:" + username;
    }
}

package com.github.lybgeek.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("echo")
public class EchoController {

    @GetMapping("{message}")
    public String echo(@PathVariable("message") String message){
        System.out.println("otherComsumer:" + message);
        return "otherComsumer ：" + message;
    }

}

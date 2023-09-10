package com.github.lybgeek.aop.test.hello.controller;


import com.github.lybgeek.aop.test.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping("{message}")
    public String sayHello(@PathVariable("message")String message){
        return helloService.sayHello(message);
    }
}

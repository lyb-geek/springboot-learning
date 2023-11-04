package com.github.lybgeek.controller;


import com.github.lybgeek.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@RequiredArgsConstructor
public class HelloController {

    private final ApplicationContext applicationContext;

    @GetMapping("{username}")
    public String sayHello(@PathVariable("username")String username){
        HelloService helloService = applicationContext.getBean(HelloService.class);
        return helloService.say(username);
    }
}

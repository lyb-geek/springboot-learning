package com.github.lybgeek.refresh.test.controller;


import com.github.lybgeek.scope.annotation.RefreshBeanScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RefreshBeanScope
public class TestController {


    @Value("${test.name: }")
    private String name;


    @GetMapping("print")
    public String print(){
        return name;
    }
}

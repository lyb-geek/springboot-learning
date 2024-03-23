package com.github.lybgeek.config.controller;


import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("env")
@RequiredArgsConstructor
@RefreshScope
public class EnvController {

    private final StandardEnvironment environment;

    public final static String HELLO_KEY = "lybgeek.hello";

    public final static String HELLO_VALUE = "${"+HELLO_KEY+":helloEnv}";

    @Value(HELLO_VALUE)
    private String hello;


    @GetMapping("properties")
    public String getProperty(){
        return JSONUtil.toJsonStr(environment.getSystemProperties());
    }

    @GetMapping("hello")
    public String hello(){
        System.out.println(environment.getProperty(HELLO_KEY));
        return hello;
    }
}

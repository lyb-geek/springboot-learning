package com.github.lybgeek.config.controller;

import com.github.lybgeek.config.auth.property.AuthProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
@RequiredArgsConstructor
public class ConfigController {

    private final AuthProperty authProperty;

    private final EnvironmentManager environmentManager;



    @GetMapping("refresh")
    public String refresh(String name,String value){
        environmentManager.setProperty(name,value);
        return "refresh success";
    }

    @GetMapping("get")
    public AuthProperty get(){
        return authProperty;
    }


}

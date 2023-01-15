package com.github.lybgeek.test.service;


import com.github.lybgeek.apt.anntation.AutoComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AutoComponent
public class EchoService {

    private final HelloService helloService;

    public void echo(){
        System.out.println(helloService.hello());
    }
}

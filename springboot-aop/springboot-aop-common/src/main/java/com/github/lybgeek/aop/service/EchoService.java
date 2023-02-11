package com.github.lybgeek.aop.service;



import com.github.lybgeek.aop.annotation.CostTimeRecoder;
import org.springframework.stereotype.Service;

@Service
public class EchoService {

    @CostTimeRecoder
    public void echo(String message){
        System.out.println("echo ->" + message);
    }


    public void hello(String username){
        System.out.println("hello -> " + username);
    }
}

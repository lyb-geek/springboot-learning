package com.github.lybgeek.circuitbreaker.test.controller;


import com.github.lybgeek.circuitbreaker.framework.annotation.CircuitBreakerMapping;
import com.github.lybgeek.circuitbreaker.test.exception.BizException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CircuitBreakerMapping(value = "/test")
public class TestController {

    @CircuitBreakerMapping(value = "/flow/{username}")
    public String flow(@PathVariable("username") String username){

        return "flow circuit breaker mapping : " + username;
    }

    @CircuitBreakerMapping(value = "/degrade/{username}")
    public String degrade(@PathVariable("username") String username){

        if("zhangsan".equals(username)){
            throw new BizException(400,String.format("illgel username --> %s",username));
        }

        return "degrade circuit breaker mapping : " + username;
    }

    @CircuitBreakerMapping(value = "/paramFlow/{username}")
    public String paramFlow(@PathVariable("username") String username){

        return "paramFlow circuit breaker mapping : " + username;
    }


    @CircuitBreakerMapping(value = "/authority/{username}",fallback = "fallback")
    public String authority(@PathVariable("username") String username,String origin){
        System.out.println("origin:-->" + origin);
        return "authority circuit breaker mapping : " + username;
    }

    @CircuitBreakerMapping(value = "/{username}",fallback = "fallback")
    public String username(@PathVariable("username") String username){

        return " circuit breaker mapping : " + username;
    }

    public String fallback(String username){

        return "fallback circuit breaker mapping : " + username;
    }
}

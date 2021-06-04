package com.github.lybgeek.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface EchoService {

    String INTERFACE_NAME = "feign";

    @GetMapping(value = "/echo/{username}")
    String echo(@PathVariable("username")String username);
}

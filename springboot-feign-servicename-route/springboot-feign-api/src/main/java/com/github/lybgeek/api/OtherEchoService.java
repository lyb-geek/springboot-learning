package com.github.lybgeek.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface OtherEchoService {

    String INTERFACE_NAME = "otherFeign";

    @GetMapping(value = "/say/{username}")
    String say(@PathVariable("username") String username);
}

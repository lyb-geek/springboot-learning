package com.github.lybgeek.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface FooService {

    String INTERFACE_NAME = "foo";

    @GetMapping(value = "/{username}")
    String foo(@PathVariable("username") String username);
}

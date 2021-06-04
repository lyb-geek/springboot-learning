package com.github.lybgeek.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BarService {


    @GetMapping(value = "/bar/{username}")
    String bar(@PathVariable("username") String username);
}

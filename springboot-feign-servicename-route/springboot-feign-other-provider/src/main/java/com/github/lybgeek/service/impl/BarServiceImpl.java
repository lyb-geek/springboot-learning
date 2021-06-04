package com.github.lybgeek.service.impl;


import com.github.lybgeek.api.BarService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarServiceImpl implements BarService {


    @Override
    public String bar(@PathVariable("username") String username) {
        return "bar->"+username;
    }
}

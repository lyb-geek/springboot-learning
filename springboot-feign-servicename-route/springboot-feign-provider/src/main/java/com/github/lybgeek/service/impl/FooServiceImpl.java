package com.github.lybgeek.service.impl;


import com.github.lybgeek.api.FooService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = FooService.INTERFACE_NAME)
public class FooServiceImpl implements FooService {


    @Override
    public String foo(@PathVariable("username") String username) {
        return "foo->"+username;
    }
}

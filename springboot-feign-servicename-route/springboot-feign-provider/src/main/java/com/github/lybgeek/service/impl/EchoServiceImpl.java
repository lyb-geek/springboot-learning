package com.github.lybgeek.service.impl;


import com.github.lybgeek.api.EchoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = EchoService.INTERFACE_NAME)
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(@PathVariable("username") String username) {

        return "hello:" + username;
    }
}

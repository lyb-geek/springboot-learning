package com.github.lybgeek.service.impl;


import com.github.lybgeek.api.OtherEchoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = OtherEchoService.INTERFACE_NAME)
public class OtherEchoServiceImpl implements OtherEchoService {


    @Override
    public String say(String username) {
        return "weclome:" + username;
    }
}

package com.github.lybgeek.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping("echo")
@RestController
public class EchoController {

    @RequestMapping("say/{sleep}")
    public String echo(@PathVariable("sleep") long sleep, String msg)  {

        if(sleep == 0){
            throw new IllegalArgumentException("sleep参数不能为0");
        }

        try {
            TimeUnit.MILLISECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "echo:" + msg;

    }
}

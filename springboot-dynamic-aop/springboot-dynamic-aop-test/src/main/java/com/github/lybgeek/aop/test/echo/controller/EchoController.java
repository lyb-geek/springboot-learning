package com.github.lybgeek.aop.test.echo.controller;


import com.github.lybgeek.aop.test.echo.service.EchoService;
import com.github.lybgeek.aop.test.hello.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("echo")
@RequiredArgsConstructor
public class EchoController {

    private final EchoService echoService;

    @GetMapping("{message}")
    public String echo(@PathVariable("message")String message){
        return echoService.echo(message);
    }
}

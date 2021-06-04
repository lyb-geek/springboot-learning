package com.github.lybgeek.controller;


import com.github.lybgeek.client.BarFeignClient;
import com.github.lybgeek.client.EchoFeignClient;
import com.github.lybgeek.client.FooFeignClient;
import com.github.lybgeek.client.OtherEchoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class ConsumerController {

    @Autowired
    private EchoFeignClient echoFeignClient;

    @Autowired
    private OtherEchoFeignClient otherEchoFeignClient;

    @Autowired
    private FooFeignClient fooFeignClient;

    @Autowired
    private BarFeignClient barFeignClient;


    @GetMapping(value = "/{username}")
    public String hello(@PathVariable("username") String username){

        return echoFeignClient.echo(username);
    }

    @GetMapping(value = "say/{username}")
    public String say(@PathVariable("username") String username){

        return otherEchoFeignClient.say(username);
    }

    @GetMapping(value = "foo/{username}")
    public String foo(@PathVariable("username") String username){

        return fooFeignClient.foo(username);
    }

    @GetMapping(value = "bar/{username}")
    public String bar(@PathVariable("username") String username){
        return barFeignClient.bar(username);
    }




}

package com.github.lybgeek.http.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EchoController {


    @GetMapping("echo")
    public ResponseEntity echo(String content, @RequestHeader("secret") String secret, @RequestHeader("clientId") String clientId){


        System.out.println(String.format("clientId: %s,ser: %s",clientId,secret));

        if("zhangsan".equals(clientId) && "123456".equals(secret)){
            return new ResponseEntity<>("hello : " + content,HttpStatus.OK);
        }

        return new ResponseEntity<>("authentication failed with clientId : " + clientId + ", secret: " + secret,HttpStatus.UNAUTHORIZED);


    }
}

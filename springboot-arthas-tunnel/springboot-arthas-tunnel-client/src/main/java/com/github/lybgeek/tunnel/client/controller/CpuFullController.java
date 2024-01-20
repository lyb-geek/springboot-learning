package com.github.lybgeek.tunnel.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cpu")
public class CpuFullController {

    @GetMapping("/full")
    public void full(){
        while(true){
            System.out.println("cpu busy....");
        }
    }
}

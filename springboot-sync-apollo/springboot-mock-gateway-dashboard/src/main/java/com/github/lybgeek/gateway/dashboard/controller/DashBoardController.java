package com.github.lybgeek.gateway.dashboard.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ops")
public class DashBoardController {


    @GetMapping("/index")
    public String index(){
        return "index";
    }
}

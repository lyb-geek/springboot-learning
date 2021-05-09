package com.github.lybgeek.controller;


import com.github.lybgeek.service.DeadLockService;
import com.github.lybgeek.service.OomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class ArthasTestController {


    @Autowired
    private DeadLockService deadLockService;

    @Autowired
    private OomService oomService;

    @GetMapping(value = "/deadLock")
    public String testDeadLock(){
       return deadLockService.deadLock();
    }

    @GetMapping(value = "/oom")
    public String testOom(){
        return oomService.oom();
    }
}

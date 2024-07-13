package com.github.lybgeek.producer.controller;

import com.github.lybgeek.producer.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.LongAdder;

@RestController
@RequestMapping("user")
public class UserController {

    private static final LongAdder LONG_ADDER = new LongAdder();


    @GetMapping("get")
    public User getUser(Long id){
        return User.builder().id(id).name("test").age(20).build();
    }


    @PostMapping("add")
    public User addUser(@RequestBody User user){
        LONG_ADDER.increment();
        return User.builder().id(LONG_ADDER.longValue())
                .name(user.getName())
                .age(user.getAge()).build();
    }


    @PostMapping("save")
    public User saveUser(User user){
        LONG_ADDER.increment();
        return User.builder().id(LONG_ADDER.longValue())
                .name(user.getName())
                .age(user.getAge()).build();
    }
}

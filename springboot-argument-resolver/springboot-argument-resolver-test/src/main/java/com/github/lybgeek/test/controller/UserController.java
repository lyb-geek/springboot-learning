package com.github.lybgeek.test.controller;


import com.github.lybgeek.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {


    @PostMapping("add")
    public User add(@RequestBody User user){
        return user;
    }
}

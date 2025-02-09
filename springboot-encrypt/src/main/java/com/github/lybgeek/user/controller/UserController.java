package com.github.lybgeek.user.controller;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;



    @GetMapping("list")
    public List<User> list(){
        return userService.list();

    }





}

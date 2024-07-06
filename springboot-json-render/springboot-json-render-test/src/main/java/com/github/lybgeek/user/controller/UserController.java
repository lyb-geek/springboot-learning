package com.github.lybgeek.user.controller;

import cn.hutool.core.util.IdUtil;
import com.github.lybgeek.json.render.enums.StatusEnums;
import com.github.lybgeek.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("get")
    @ResponseBody
    public User getUser(){
       return User.builder().name("张三")
                .password("123456")
                .status(StatusEnums.NORMAL)
                .id(987654321123456789L).build();
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }


    @RequestMapping("add")
    @ResponseBody
    public User addUser(@RequestBody User user){
        System.out.println(user.getStatus());
        return User.builder().name(user.getName())
                .password(user.getPassword())
                .status(user.getStatus())
                .id(IdUtil.getSnowflake(0,0).nextId()).build();
    }
}

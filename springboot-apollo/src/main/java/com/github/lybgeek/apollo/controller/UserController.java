package com.github.lybgeek.apollo.controller;

import com.github.lybgeek.apollo.convert.UserMapper;
import com.github.lybgeek.apollo.model.User;
import com.github.lybgeek.apollo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private User user;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value="/user")
    public UserVO getUser(){
        return userMapper.convertDO2VO(user);
    }
}

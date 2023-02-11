package com.github.lybgeek.test.service;


import com.github.lybgeek.apt.annotation.CostTimeRecoder;
import com.github.lybgeek.test.model.User;

import java.util.UUID;


public class UserService {


    @CostTimeRecoder
    public User opsUser(User user,boolean isAdd){
        if(isAdd){
            user.setId(UUID.randomUUID().toString());
        }
        return user;
    }
}

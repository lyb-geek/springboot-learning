package com.github.lybgeek.redis.user.controller;


import com.github.lybgeek.mysql.check.MySqlValidConnectionExtChecker;
import com.github.lybgeek.redis.user.cache.UserCache;
import com.github.lybgeek.redis.user.entity.User;
import com.github.lybgeek.redis.user.service.UserService;
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

    private final UserCache userCache;


    @GetMapping("list")
    public List<User> list(){
        if(MySqlValidConnectionExtChecker.isMySQLCommunicationsException.get()){
            return userCache.getUsersFromCache();
        }
        try {
            return userService.list();
        } catch (Exception e) {

        }
        return userCache.getUsersFromCache();

    }



}

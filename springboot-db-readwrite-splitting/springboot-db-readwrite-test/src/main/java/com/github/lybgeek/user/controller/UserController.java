package com.github.lybgeek.user.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.javafaker.Faker;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;


    @GetMapping("list")
    @DS("slave")
    public List<User> list(){
        try {
            return userService.list();
        } catch (Exception e) {

        }
        return defaultUsers();

    }


    @GetMapping("save")
    @DS("master")
    public String save(){
        User user = buildUser();
        return userService.save(user) ? "save success" : "save fail";

    }

    private User buildUser() {
        Faker faker = Faker.instance(Locale.CHINA);
        String fullName = faker.name().fullName();
        String username = PinyinUtil.getPinyin(fullName,"");
        String email = username + "@example.com";
        String mobile = faker.phoneNumber().phoneNumber();
        String password = RandomUtil.randomString(6);
        User user = User.builder().email(email)
                .fullname(fullName)
                .username(username)
                .mobile(mobile)
                .password(password)
                .build();
        return user;
    }

    private List<User> defaultUsers(){
        List<User> users = new ArrayList<>();
        User user = User.builder().email("defaultUser@qq.com")
                .fullname("默认用户")
                .username("defaultUser")
                .mobile("13600000011")
                .password("123456")
                .id(-1)
                .build();
        users.add(user);
        return users;
    }




}

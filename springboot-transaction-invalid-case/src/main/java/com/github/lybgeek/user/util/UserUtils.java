package com.github.lybgeek.user.util;


import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.lybgeek.user.entity.User;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class UserUtils {

    public static User getUser(){
        User user = new User();
        Faker faker = Faker.instance(Locale.CHINA);
        Name name = faker.name();
        Map<String,String> userNameMap = new HashMap<>();
        userNameMap.put("username",name.username());
        user.setEmail(userNameMap.get("username") + "@qq.com");
        user.setFullname(userNameMap.get("username") );
        user.setMobile(faker.phoneNumber().phoneNumber());
        user.setPassword(UUID.randomUUID().toString());
        user.setUsername(userNameMap.get("username") );
        return user;
    }
}

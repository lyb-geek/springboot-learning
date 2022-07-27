package com.github.lybgeek.redis.ext.test.service;


import com.github.javafaker.Faker;
import com.github.lybgeek.redis.cacheable.annotation.LybGeekCacheable;

import com.github.lybgeek.redis.ext.test.model.User;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {



    @LybGeekCacheable(cacheNames = "customUser", key = "#id",expiredTimeSecond = 30)
    public User getUserFromRedisByCustomAnno(String id){
        System.out.println("get user with id by custom anno: 【" + id + "】");
        Faker faker = Faker.instance(Locale.CHINA);
        return User.builder().id(id).username(faker.name().username()).build();

    }

    @LybGeekCacheable(cacheNames = "customUserName", key = "#username",expiredTimeSecond = 20,preLoadTimeSecond = 15)
    public User getUserFromRedisByCustomAnnoWithUserName(String username){
        System.out.println("get user with username by custom anno: 【" + username + "】");
        Faker faker = Faker.instance(Locale.CHINA);
        return User.builder().id(faker.idNumber().valid()).username(username).build();

    }
}

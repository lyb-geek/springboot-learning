package com.github.lybgeek.redis.test.service;


import com.github.javafaker.Faker;
import com.github.lybgeek.redis.test.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {


    @Cacheable(cacheNames = "user",key = "#id")
    public User getUserFromRedis(String id){
        System.out.println("get user with id : 【" + id + "】");
        Faker faker = Faker.instance(Locale.CHINA);
        return User.builder().id(id).username(faker.name().username()).build();

    }


}

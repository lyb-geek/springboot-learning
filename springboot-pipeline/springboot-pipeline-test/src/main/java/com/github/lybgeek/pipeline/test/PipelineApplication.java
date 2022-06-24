package com.github.lybgeek.pipeline.test;


import com.github.javafaker.Faker;
import com.github.lybgeek.pipeline.test.model.User;
import com.github.lybgeek.pipeline.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class PipelineApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(PipelineApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Faker faker = Faker.instance(Locale.CHINA);
        User user = User.builder().age(20)
                .fullname(faker.name().fullName())
                .mobile(faker.phoneNumber().phoneNumber())
                .password("123456").build();
        userService.save(user);
    }
}

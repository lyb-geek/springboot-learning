package com.github.lybgeek.p6spy;


import com.github.lybgeek.p6spy.user.entity.User;
import com.github.lybgeek.p6spy.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.p6spy.**.dao")
public class P6spyApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(P6spyApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> list = userService.list();
        System.out.println(list);

    }
}

package com.github.lybgeek.ds.switchover.test;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import com.github.lybgeek.ds.switchover.test.user.entity.User;
import com.github.lybgeek.ds.switchover.test.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.ds.switchover.test.**.dao")
@EnableApolloConfig
public class DsSwitchoverTestApplication implements ApplicationRunner {

    @Autowired
    private UserService userService;



    public static void main(String[] args) {
        SpringApplication.run(DsSwitchoverTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while(true){
            User user = userService.getById(1L);
            System.err.println(user.getPassword());
            TimeUnit.SECONDS.sleep(1);
        }



    }
}

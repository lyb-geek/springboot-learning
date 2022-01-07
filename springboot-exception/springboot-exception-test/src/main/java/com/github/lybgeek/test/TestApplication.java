package com.github.lybgeek.test;


import com.dtflys.forest.springboot.annotation.ForestScan;
import com.github.lybgeek.test.user.client.UserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ForestScan(basePackageClasses = UserClient.class)
public class TestApplication {



    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }


}

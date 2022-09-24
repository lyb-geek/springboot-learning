package com.github.lybgeek.db.check.test;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek")
@MapperScan(basePackages = "com.github.lybgeek.db.check.test.**.dao")
public class Application implements ApplicationRunner {



    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


    }
}

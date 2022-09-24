package com.github.lybgeek.redis;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "com.github.lybgeek.redis")
@MapperScan(basePackages = "com.github.lybgeek.redis.**.dao")
public class Application implements ApplicationRunner {

    @Autowired
    private LettuceRedisTemplate lettuceRedisTemplate;


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while(true){
            System.out.println(lettuceRedisTemplate.getClientCacheValue("zhangsan"));
            TimeUnit.SECONDS.sleep(1);
        }


    }
}

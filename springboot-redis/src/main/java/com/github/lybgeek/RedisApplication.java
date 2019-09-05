package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.lybgeek.redis.dao"})
@EnableCaching
public class RedisApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(RedisApplication.class,args);
    }
}

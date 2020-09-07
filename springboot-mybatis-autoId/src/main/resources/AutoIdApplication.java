package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.github.lybgeek.orm.mybatis.dao","com.github.lybgeek.orm.mybatisplus.dao"})
public class AutoIdApplication {
    public static void main( String[] args ) {

        SpringApplication.run(AutoIdApplication.class,args);
    }
}

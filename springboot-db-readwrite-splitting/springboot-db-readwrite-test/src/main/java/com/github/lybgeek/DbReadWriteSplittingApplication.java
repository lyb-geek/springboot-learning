package com.github.lybgeek;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = "com.github.lybgeek.**.dao")
public class DbReadWriteSplittingApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbReadWriteSplittingApplication.class, args);
    }
}
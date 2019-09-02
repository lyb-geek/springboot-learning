package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.lybgeek.spilt.dao"})
public class SplitTableApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(SplitTableApplication.class,args);
    }
}

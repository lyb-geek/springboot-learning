package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.lybgeek.dynamic.dao"})
public class DynamicDataSourceApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(DynamicDataSourceApplication.class,args);
    }
}

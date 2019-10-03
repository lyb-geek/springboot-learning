package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.lybgeek.swagger.dao"})
@EnableSwagger2
public class SwaggerSourceApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(SwaggerSourceApplication.class,args);
    }
}

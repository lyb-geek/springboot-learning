package com.github.lybgeek.virtualcolumn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.virtualcolumn.**.dao")
public class MysqlVirtualColumnApplication {




    public static void main(String[] args) {
        SpringApplication.run(MysqlVirtualColumnApplication.class);
    }


}

package com.github.lybgeek.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.**.dao")
public class SpringbootMybatisplusTenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisplusTenantApplication.class, args);
	}

}

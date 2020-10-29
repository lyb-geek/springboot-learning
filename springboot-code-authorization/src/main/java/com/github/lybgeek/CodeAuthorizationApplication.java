package com.github.lybgeek;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.**.dao")
public class CodeAuthorizationApplication {


	public static void main(String[] args) {
		SpringApplication.run(CodeAuthorizationApplication.class, args);
	}


}

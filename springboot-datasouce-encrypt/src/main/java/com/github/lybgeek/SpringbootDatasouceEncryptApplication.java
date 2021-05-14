package com.github.lybgeek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.github.lybgeek.**.dao")
public class SpringbootDatasouceEncryptApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDatasouceEncryptApplication.class, args);
	}

}

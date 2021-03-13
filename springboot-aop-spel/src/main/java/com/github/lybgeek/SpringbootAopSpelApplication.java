package com.github.lybgeek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootAopSpelApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootAopSpelApplication.class, args);
	}


}

package com.github.lybgeek;

import com.github.lybgeek.mock.service.client.RpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootJavaagentTestApplication implements ApplicationRunner {

	@Autowired
	private RpcClientService rpcClientService;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJavaagentTestApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(rpcClientService.send("hello world!"));
	}
}

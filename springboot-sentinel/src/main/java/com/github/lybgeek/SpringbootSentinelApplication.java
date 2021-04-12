package com.github.lybgeek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.cloud.client.discovery.noop.NoopDiscoveryClientAutoConfiguration;

@SpringBootApplication(exclude = {CommonsClientAutoConfiguration.class,NoopDiscoveryClientAutoConfiguration.class})
public class SpringbootSentinelApplication  {



	public static void main(String[] args) {
		SpringApplication.run(SpringbootSentinelApplication.class, args);
	}


}

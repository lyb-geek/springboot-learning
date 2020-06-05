package com.github.lybgeek.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableApolloConfig(value = {"application","user.properties","product.properties","order.properties"})
@EnableAspectJAutoProxy
public class ApolloApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApolloApplication.class, args);
	}

}
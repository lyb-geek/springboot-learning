package com.github.lybgeek;

import com.github.lybgeek.common.jpa.repository.CustomSimpleJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.github.lybgeek.shorturl.repository",repositoryBaseClass = CustomSimpleJpaRepository.class)
public class ElasticsearchApplication {

	public static void main(String[] args) {
		/**
		 * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
		 * 解决netty冲突后初始化client时还会抛出异常
		 * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
		 */
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(ElasticsearchApplication.class, args);
	}

}
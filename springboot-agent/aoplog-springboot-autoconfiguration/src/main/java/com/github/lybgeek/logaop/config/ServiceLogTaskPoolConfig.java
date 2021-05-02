package com.github.lybgeek.logaop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

 @EnableAsync
 @Configuration
 public class ServiceLogTaskPoolConfig {

  @Bean
  public Executor serviceLogTaskExecutor() {
     ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
     executor.setCorePoolSize(10);
     executor.setMaxPoolSize(20);
     executor.setQueueCapacity(200);
     executor.setKeepAliveSeconds(60);
     executor.setThreadNamePrefix("svcLog-pool-");
     executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
     return executor;
  }
 }

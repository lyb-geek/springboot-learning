package com.github.lybgeek.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Serializable;

@Configuration
@PropertySource(value="classpath:druid.properties",ignoreResourceNotFound = true,encoding="utf-8")
@ConfigurationProperties(prefix = "druid")
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConfig implements Serializable {

  private String driverClassName;

  private String url;

  private String username;

  private String password;

  private String filters;

  private Integer initialSize;

  private Integer maxActive;

  private Integer maxWait;

  private Integer timeBetweenEvictionRunsMillis;

  private Integer minEvictableIdleTimeMillis;

  private String validationQuery;

  private boolean testWhileIdle;

  private boolean testOnBorrow;

  private boolean testOnReturn;




}

package com.github.lybgeek.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "env")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvConfig {

  //区分环境，dev为开发环境，prod为生成环境
  private String profie;
}

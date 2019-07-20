package com.github.lybgeek.model;

import com.github.lybgeek.factory.YamlPropertySourceFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class,value = {"classpath:user.yml"},ignoreResourceNotFound=false,encoding="UTF-8")
@ConfigurationProperties(prefix = "user")
public class User {
    private String username;

    private String password;
}

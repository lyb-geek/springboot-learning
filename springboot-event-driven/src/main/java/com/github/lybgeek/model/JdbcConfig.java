package com.github.lybgeek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(value = "classpath:jdbc.properties",ignoreResourceNotFound = true,encoding = "UTF-8")
@ConfigurationProperties(prefix = "jdbc")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class JdbcConfig {
    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private static String aliasName;

    @Value("${jdbc.username}")
    private void setAliasName(String username){
        aliasName = username;
    }

    public String getAliasName(){
        return aliasName;
    }

    @PostConstruct
    private void init(){
        log.info("{}",this);
        log.info("aliasName:{}",this.getAliasName());
    }


}

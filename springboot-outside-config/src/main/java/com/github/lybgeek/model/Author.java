package com.github.lybgeek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
@PropertySource(value = {"classpath:common.properties"},ignoreResourceNotFound=false,encoding="UTF-8")
@ConfigurationProperties(prefix = "author")
public class Author {
    private String name;
    private String job;
    private String sex;

    private static String nickName;

    @Value("${author.name}")
    public void setNickName(String name){
        nickName = name;
    }

    public String getNickName(){
        return nickName;
    }
}

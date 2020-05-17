package com.github.lybgeek.apollo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    private Long id;

    private String userName;


    private Long age;


}

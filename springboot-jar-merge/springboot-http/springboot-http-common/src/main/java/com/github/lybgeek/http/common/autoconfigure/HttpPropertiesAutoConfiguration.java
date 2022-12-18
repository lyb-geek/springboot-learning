package com.github.lybgeek.http.common.autoconfigure;


import com.github.lybgeek.http.common.properties.HttpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HttpProperties.class)
public class HttpPropertiesAutoConfiguration {

}

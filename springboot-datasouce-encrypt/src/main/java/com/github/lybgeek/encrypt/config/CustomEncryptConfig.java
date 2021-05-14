package com.github.lybgeek.encrypt.config;

import com.github.lybgeek.encrypt.process.DruidDataSourceEncyptBeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@EnableConfigurationProperties(CustomEncryptProperties.class)
@Profile("dbCustomEncrypt")
@ConditionalOnProperty(prefix = "custom.encrypt",name = "enabled",havingValue = "true")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class CustomEncryptConfig {


    @Bean
    @ConditionalOnMissingBean
    public DruidDataSourceEncyptBeanPostProcessor datasourceEncyptBeanFactoryPostProcessor(CustomEncryptProperties customEncryptProperties, DataSourceProperties dataSourceProperties){
        return new DruidDataSourceEncyptBeanPostProcessor(customEncryptProperties,dataSourceProperties);
    }
}

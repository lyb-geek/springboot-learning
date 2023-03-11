package com.github.lybgeek.sms.config;


import com.github.lybgeek.sms.enums.SmsType;
import com.github.lybgeek.sms.provider.factory.SmsFactory;
import com.github.lybgeek.sms.service.SmsService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


@Configuration
@ComponentScan(basePackages = "com.github.lybgeek.sms")
public class SmsPluginActiveConfig {


    @Bean
    @ConditionalOnMissingBean
    public FactoryBean smsFactory(){
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(SmsFactory.class);
        // spring beanName映射，自定义名称映射关系,
        Properties properties = new Properties();
        properties.setProperty(SmsType.ALIYUN.toString(),"aliyunSmsProvider");
        properties.setProperty(SmsType.TENCENT.toString(),"tencentSmsProvider");
        serviceLocatorFactoryBean.setServiceMappings(properties);
        return serviceLocatorFactoryBean;
    }


    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService(ObjectProvider<SmsFactory> smsFactory){
        return new SmsService( smsFactory.getIfAvailable());
    }
}

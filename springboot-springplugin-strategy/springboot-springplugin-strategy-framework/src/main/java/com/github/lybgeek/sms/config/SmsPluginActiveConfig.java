package com.github.lybgeek.sms.config;


import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.plugin.SmsPlugin;
import com.github.lybgeek.sms.service.SmsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;

@EnablePluginRegistries(SmsPlugin.class)
@Configuration
@ComponentScan(basePackages = "com.github.lybgeek.sms")
public class SmsPluginActiveConfig {


    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService(ObjectProvider<PluginRegistry<SmsPlugin, SmsRequest>> registries){
        return new SmsService(registries.getIfAvailable());
    }
}

package com.github.lybgeek.propoutconfig.autoconfigure;


import com.github.lybgeek.propoutconfig.service.MockSmsService;
import com.github.lybgeek.sms.config.SmsConfig;
import com.github.lybgeek.sms.service.SmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "lybgeek.sms")
    public SmsConfig smsConfig(){
        return new SmsConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService(SmsConfig smsConfig){
        return new MockSmsService(smsConfig);
    }
}

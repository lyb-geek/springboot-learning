package com.github.lybgeek.sms.core.named;


import com.github.lybgeek.sms.SmsService;
import com.github.lybgeek.sms.support.DefaultSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultSmsClientConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService(){
        return new DefaultSmsService();
    }


}

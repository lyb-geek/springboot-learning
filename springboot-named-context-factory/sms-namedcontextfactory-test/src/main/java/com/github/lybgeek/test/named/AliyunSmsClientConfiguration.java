package com.github.lybgeek.test.named;


import com.github.lybgeek.sms.SmsService;
import com.github.lybgeek.sms.support.AliyunSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class AliyunSmsClientConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public SmsService smsService() {
       return new AliyunSmsService();
    }
}

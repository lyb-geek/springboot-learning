package com.github.lybgeek.event.condition.sms.autoconfigure;

import com.github.lybgeek.event.condition.sms.listener.AliyunSmsListener;
import com.github.lybgeek.event.condition.sms.listener.TencentSmsListener;
import com.github.lybgeek.event.condition.sms.service.support.AliyunSmsService;
import com.github.lybgeek.event.condition.sms.service.support.TencentSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;


@Configuration
public class SmsAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public AliyunSmsService aliyunSmsService(){
        return new AliyunSmsService();
    }


    @Bean
    @ConditionalOnMissingBean
    public TencentSmsService tencentSmsService(){
        return new TencentSmsService();
    }


    @Bean
    @ConditionalOnMissingBean
    public AliyunSmsListener aliyunSmsListener(AliyunSmsService aliyunSmsService){
        return new AliyunSmsListener(aliyunSmsService);
    }


    @Bean
    @ConditionalOnMissingBean
    public TencentSmsListener tencentSmsListener(TencentSmsService tencentSmsListener){
        return new TencentSmsListener(tencentSmsListener);
    }








}

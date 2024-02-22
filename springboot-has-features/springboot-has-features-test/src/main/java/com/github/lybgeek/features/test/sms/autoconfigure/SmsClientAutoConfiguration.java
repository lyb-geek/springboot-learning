package com.github.lybgeek.features.test.sms.autoconfigure;


import com.github.lybgeek.features.test.sms.AliyunSmsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SmsClientAutoConfiguration {


    @Bean
  public AliyunSmsService aliyunSmsService() {
    return new AliyunSmsService();
  }



}

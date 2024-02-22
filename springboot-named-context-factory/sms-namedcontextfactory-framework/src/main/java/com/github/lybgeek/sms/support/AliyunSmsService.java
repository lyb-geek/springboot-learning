package com.github.lybgeek.sms.support;


import com.github.lybgeek.sms.SmsService;

public class AliyunSmsService implements SmsService {
    @Override
    public void send(String phone, String content) {
        System.out.printf("send to %s content %s used aliyun sms%n", phone, content);
    }
}

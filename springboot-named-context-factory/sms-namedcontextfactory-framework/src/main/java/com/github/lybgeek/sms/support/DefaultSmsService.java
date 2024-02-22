package com.github.lybgeek.sms.support;


import com.github.lybgeek.sms.SmsService;

public class DefaultSmsService implements SmsService {
    @Override
    public void send(String phone, String content) {
        System.out.printf("send to %s content %s used default sms%n", phone, content);
    }
}

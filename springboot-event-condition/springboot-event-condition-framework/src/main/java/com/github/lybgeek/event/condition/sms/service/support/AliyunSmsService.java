package com.github.lybgeek.event.condition.sms.service.support;


import com.github.lybgeek.event.condition.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;


public class AliyunSmsService implements SmsService {
    @Override
    public void sendSms(String phone, String content) {
        System.out.printf("%s->使用阿里云短信【%s】发送成功！%n",phone,content);
    }
}

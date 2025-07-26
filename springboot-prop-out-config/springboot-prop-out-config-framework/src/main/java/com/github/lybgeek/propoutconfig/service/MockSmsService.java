package com.github.lybgeek.propoutconfig.service;


import com.github.lybgeek.sms.config.SmsConfig;
import com.github.lybgeek.sms.service.SmsService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockSmsService implements SmsService {

    private final SmsConfig smsConfig;
    @Override
    public boolean sendSms(String phoneNumber, String templateParam) {
        System.out.printf("%s phoneNumber:%s templateParam:%s%n", getClass().getName(),phoneNumber, templateParam);
        System.out.printf("%s smsConfig:%s%n", getClass().getName(), smsConfig);
        return true;
    }
}

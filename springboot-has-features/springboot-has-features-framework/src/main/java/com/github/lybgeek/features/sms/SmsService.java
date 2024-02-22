package com.github.lybgeek.features.sms;


public interface SmsService {

    void send(String phone, String content);
}

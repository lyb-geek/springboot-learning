package com.github.lybgeek.sms.service;


public interface SmsService {

    boolean sendSms(String phoneNumber, String templateParam);
}

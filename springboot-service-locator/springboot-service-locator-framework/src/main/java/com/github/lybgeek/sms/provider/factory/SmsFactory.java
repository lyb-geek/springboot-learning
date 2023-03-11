package com.github.lybgeek.sms.provider.factory;


import com.github.lybgeek.sms.enums.SmsType;
import com.github.lybgeek.sms.provider.SmsProvider;

public interface SmsFactory {

    SmsProvider getProvider(SmsType smsType);
}

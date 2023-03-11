package com.github.lybgeek.sms.provider;


import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;

public interface SmsProvider {


    SmsResponse sendSms(SmsRequest smsRequest);


}

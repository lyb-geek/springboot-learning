package com.github.lybgeek.sms.plugin;


import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import org.springframework.plugin.core.Plugin;

public interface SmsPlugin extends Plugin<SmsRequest> {


    SmsResponse sendSms(SmsRequest smsRequest);


}

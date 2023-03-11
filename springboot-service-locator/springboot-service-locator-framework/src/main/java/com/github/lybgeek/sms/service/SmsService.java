package com.github.lybgeek.sms.service;


import com.github.lybgeek.sms.exception.SmsException;
import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.provider.factory.SmsFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


@RequiredArgsConstructor
public class SmsService {


    private final SmsFactory smsFactory;


    public SmsResponse sendSms(SmsRequest smsRequest){

        return Optional.of(smsFactory.getProvider(smsRequest.getSmsType())).orElseThrow(() -> new SmsException("Sms plugin is not binder with type : 【" + smsRequest.getSmsType() + "】"))
                .sendSms(smsRequest);


    }
}

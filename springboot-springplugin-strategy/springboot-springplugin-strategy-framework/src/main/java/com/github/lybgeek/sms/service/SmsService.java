package com.github.lybgeek.sms.service;


import com.github.lybgeek.sms.exception.SmsException;
import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.plugin.SmsPlugin;
import lombok.RequiredArgsConstructor;
import org.springframework.plugin.core.PluginRegistry;

import java.util.Optional;


@RequiredArgsConstructor
public class SmsService {


    private final PluginRegistry<SmsPlugin,SmsRequest> pluginRegistry;


    public SmsResponse sendSms(SmsRequest smsRequest){
        Optional<SmsPlugin> smsPlugin = pluginRegistry.getPluginFor(smsRequest);
        return smsPlugin.orElseThrow(() -> new SmsException("Sms plugin is not binder with type : 【" + smsRequest.getSmsType() + "】"))
                .sendSms(smsRequest);


    }
}

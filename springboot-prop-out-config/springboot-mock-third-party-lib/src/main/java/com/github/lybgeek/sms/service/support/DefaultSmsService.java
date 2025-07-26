package com.github.lybgeek.sms.service.support;


import com.github.lybgeek.sms.config.SmsConfig;
import com.github.lybgeek.sms.service.SmsService;
import com.github.lybgeek.sms.util.PropertiesLoader;
import com.github.lybgeek.sms.util.SmsConfigUtil;

import java.io.IOException;
import java.util.Properties;

public class DefaultSmsService implements SmsService {

    private final SmsConfig smsConfig;

    public DefaultSmsService(String... configNames)  {
        try {
            this.smsConfig = initSmsConfig(configNames);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SmsConfig initSmsConfig(String[] configNames) throws IOException {
        String configName = "sms.properties";
        if(configNames != null && configNames.length > 0){
            configName = configNames[0];
        }
        Properties properties = PropertiesLoader.loadFromClasspath(configName);
        return SmsConfigUtil.fromProperties(properties);
    }

    @Override
    public boolean sendSms(String phoneNumber, String templateParam) {
        System.out.printf("%s phoneNumber:%s templateParam:%s%n", getClass().getName(),phoneNumber, templateParam);
        System.out.printf("%s smsConfig:%s%n", getClass().getName(), smsConfig);
        return true;
    }
}

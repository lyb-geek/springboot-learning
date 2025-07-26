package com.github.lybgeek.sms.util;


import com.github.lybgeek.sms.config.SmsConfig;

import java.util.Properties;

public final class SmsConfigUtil {

    private SmsConfigUtil(){}

    public static SmsConfig fromProperties(Properties properties) {
        return new SmsConfig(
                properties.getProperty("lybgeek.sms.accessKeyId"),
                properties.getProperty("lybgeek.sms.accessKeySecret"),
                properties.getProperty("lybgeek.sms.signName"),
                properties.getProperty("lybgeek.sms.templateCode"),
                properties.getProperty("lybgeek.sms.endpoint"),
                properties.getProperty("lybgeek.sms.regionId")
        );
    }
}

package com.github.lybgeek.sms.aliyun;

import com.github.lybgeek.sms.enums.SmsType;
import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.plugin.SmsPlugin;
import org.springframework.stereotype.Component;


@Component
public class AliyunSmsPlugin implements SmsPlugin {
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        System.out.println("来自阿里云短信：" + smsRequest);
        return SmsResponse.builder()
                .code("200").message("发送成功")
                .success(true).result("阿里云短信的回执").build();
    }

    @Override
    public boolean supports(SmsRequest smsRequest) {
        return SmsType.ALIYUN == smsRequest.getSmsType();
    }
}

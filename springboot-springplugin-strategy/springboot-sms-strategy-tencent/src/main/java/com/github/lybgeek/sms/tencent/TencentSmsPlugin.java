package com.github.lybgeek.sms.tencent;

import com.github.lybgeek.sms.enums.SmsType;
import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.plugin.SmsPlugin;
import org.springframework.stereotype.Component;


@Component
public class TencentSmsPlugin implements SmsPlugin {
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        System.out.println("来自腾讯云短信：" + smsRequest);
        return SmsResponse.builder()
                .code("200").message("发送成功")
                .success(true).result("腾讯云短信的回执").build();
    }

    @Override
    public boolean supports(SmsRequest smsRequest) {
        return SmsType.TENCENT == smsRequest.getSmsType();
    }
}

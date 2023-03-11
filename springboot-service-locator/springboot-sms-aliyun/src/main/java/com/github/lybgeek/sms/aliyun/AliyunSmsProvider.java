package com.github.lybgeek.sms.aliyun;

import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.provider.SmsProvider;
import org.springframework.stereotype.Component;


@Component
public class AliyunSmsProvider implements SmsProvider {
    @Override
    public SmsResponse sendSms(SmsRequest smsRequest) {
        System.out.println("来自阿里云短信：" + smsRequest);
        return SmsResponse.builder()
                .code("200").message("发送成功")
                .success(true).result("阿里云短信的回执").build();
    }


}

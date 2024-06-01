package com.github.lybgeek.event.condition.sms.listener;


import com.github.lybgeek.event.condition.sms.event.SmsEvent;
import com.github.lybgeek.event.condition.sms.service.support.AliyunSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class AliyunSmsListener {

    private final AliyunSmsService aliyunSmsService;

    @EventListener(condition = "#smsEvent.smsEnums.name().equalsIgnoreCase('aliyun')")
    public void listener(SmsEvent smsEvent){
        aliyunSmsService.sendSms(smsEvent.getPhone(),smsEvent.getContent());
    }

}

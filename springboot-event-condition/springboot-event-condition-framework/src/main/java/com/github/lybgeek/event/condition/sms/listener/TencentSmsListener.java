package com.github.lybgeek.event.condition.sms.listener;

import com.github.lybgeek.event.condition.sms.event.SmsEvent;
import com.github.lybgeek.event.condition.sms.service.support.TencentSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class TencentSmsListener {

    private final TencentSmsService tencentSmsService;

    @EventListener(condition = "#smsEvent.smsEnums.name().equalsIgnoreCase('tencent')")
    public void listener(SmsEvent smsEvent){
        tencentSmsService.sendSms(smsEvent.getPhone(),smsEvent.getContent());
    }

}

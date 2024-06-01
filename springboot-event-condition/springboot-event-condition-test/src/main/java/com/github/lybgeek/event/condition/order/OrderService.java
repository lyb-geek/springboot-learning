package com.github.lybgeek.event.condition.order;


import com.github.lybgeek.event.condition.sms.enums.SmsEnums;
import com.github.lybgeek.event.condition.sms.event.SmsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final ApplicationContext applicationContext;



    public void mockOrder(String username,String phone) {
        System.out.println("用户：" + username + " 创建订单成功");
        SmsEvent smsEvent = SmsEvent.builder().phone(phone).content("订单创建成功").smsEnums(SmsEnums.TENCENT).build();
        applicationContext.publishEvent(smsEvent);
    }
}

package com.github.lybgeek.event.condition.user;


import com.github.lybgeek.event.condition.sms.enums.SmsEnums;
import com.github.lybgeek.event.condition.sms.event.SmsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationContext applicationContext;

    public void mockRegister(String username,String phone){
        System.out.println("用户注册成功，用户名："+username);
        SmsEvent smsEvent = SmsEvent.builder().phone(phone).content("欢迎注册").smsEnums(SmsEnums.ALIYUN).build();
        applicationContext.publishEvent(smsEvent);
    }
}

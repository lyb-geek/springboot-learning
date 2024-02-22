package com.github.lybgeek.test.user.listener;


import com.github.lybgeek.sms.SmsService;
import com.github.lybgeek.sms.core.named.SmsClientNameContextFactory;
import com.github.lybgeek.sms.support.AliyunSmsService;
import com.github.lybgeek.test.user.UserService;
import com.github.lybgeek.test.user.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterListener {

    private final SmsClientNameContextFactory smsClientNameContextFactory;


    @EventListener
    @Async
    public void listener(UserRegisterEvent event) {
        SmsService smsService = smsClientNameContextFactory.getSmsService(UserService.SERVICE_NAME);
        smsService.send(event.getMobile(), "恭喜您注册成功！初始密码为："+event.getPassword()+"，请尽快修改密码！");
    }
}

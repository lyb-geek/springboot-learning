package com.github.lybgeek.test.named;


import com.github.lybgeek.sms.core.annotation.SmsClient;
import com.github.lybgeek.sms.core.annotation.SmsClients;
import com.github.lybgeek.test.order.OrderService;
import com.github.lybgeek.test.user.UserService;
import org.springframework.context.annotation.Configuration;

@Configuration
@SmsClients(value = {@SmsClient(name = OrderService.SERVICE_NAME, configuration = AliyunSmsClientConfiguration.class),
        @SmsClient(name = UserService.SERVICE_NAME, configuration = HuaWeiSmsClientConfiguration.class)})
public class SmsClientAutoConfiguration {
}

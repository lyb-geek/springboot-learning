package com.github.lybgeek.propoutconfig.test;

import com.github.lybgeek.sms.service.SmsService;
import com.github.lybgeek.sms.service.support.DefaultSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PropOutConfigTestApplication implements ApplicationRunner {

    private final SmsService smsService;
    public static void main(String[] args) {
       SpringApplication.run(PropOutConfigTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        smsService.sendSms("18888888888", "123456");
        System.out.println("======================================================");
        new DefaultSmsService().sendSms("18899999999", "00000000000");

    }
}

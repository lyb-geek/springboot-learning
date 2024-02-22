package com.github.lybgeek.sms.core.named;


import com.github.lybgeek.sms.SmsService;
import org.springframework.cloud.context.named.NamedContextFactory;

public class SmsClientNameContextFactory extends NamedContextFactory<SmsClientSpecification> {

    public SmsClientNameContextFactory() {
        super(DefaultSmsClientConfiguration.class, "sms", "sms.client.name");
    }

    public SmsService getSmsService(String serviceName) {
        return getInstance(serviceName, SmsService.class);
    }
}

package com.github.lybgeek.sms.core.autoconfigure;

import com.github.lybgeek.sms.core.named.SmsClientNameContextFactory;
import com.github.lybgeek.sms.core.named.SmsClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SmsClientNameContextFactoryAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public SmsClientNameContextFactory smsClientNameContextFactory(@Autowired(required = false) List<SmsClientSpecification> smsSpecifications){
        SmsClientNameContextFactory smsClientNameContextFactory = new SmsClientNameContextFactory();
        smsClientNameContextFactory.setConfigurations(smsSpecifications);
        return smsClientNameContextFactory;
    }



}

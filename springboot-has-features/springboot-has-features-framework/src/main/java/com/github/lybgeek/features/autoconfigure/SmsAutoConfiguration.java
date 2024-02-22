package com.github.lybgeek.features.autoconfigure;


import com.github.lybgeek.features.sms.SmsService;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.actuator.NamedFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SmsAutoConfiguration {



    @Bean
  public HasFeatures smsFeatures(){
        HasFeatures features = HasFeatures.abstractFeatures(SmsService.class);
        features.getNamedFeatures().add(new NamedFeature("sms auto configuration",SmsAutoConfiguration.class ));
        return features;
  }


}

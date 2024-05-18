package com.github.lybgeek.thirdparty.autoconfigure;

import com.github.lybgeek.thirdparty.processor.ThirdpartyBeanReplaceBeanPostProcessor;
import com.github.lybgeek.thirdparty.property.ThirdpartyBeanReplaceProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ThirdpartyBeanReplaceProperty.class)
@ConditionalOnProperty(prefix = ThirdpartyBeanReplaceProperty.PREFIX,name = "bean-replace",havingValue = "true",matchIfMissing = true)
public class ThirdpartyBeanReplaceAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public static ThirdpartyBeanReplaceBeanPostProcessor thirdpartyBeanReplaceBeanPostProcessor(ThirdpartyBeanReplaceProperty thirdpartyBeanReplaceProperty){
        return new ThirdpartyBeanReplaceBeanPostProcessor(thirdpartyBeanReplaceProperty);
    }



}

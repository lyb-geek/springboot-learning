package com.github.lybgeek.jsonview.autoconfigure;


import com.github.lybgeek.jsonview.factory.init.JsonViewBeanPostProcessor;
import com.github.lybgeek.jsonview.property.JsonViewProperty;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(JsonViewProperty.class)
public class JsonViewAutoConfiguration {




    @Bean
    @ConditionalOnMissingBean
    public static JsonViewBeanPostProcessor jsonViewBeanPostProcessor(JsonViewProperty jsonViewProperty,DefaultListableBeanFactory defaultListableBeanFactory){
        return new JsonViewBeanPostProcessor(defaultListableBeanFactory,jsonViewProperty);
    }

}

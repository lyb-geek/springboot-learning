package com.github.lybgeek.loadbalance.autoconfigure;


import com.github.lybgeek.loadbalance.LoadbalanceFactory;
import com.github.lybgeek.loadbalance.property.LoadBalanceProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LoadBalanceProperty.class)
public class LoadbalanceAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public LoadbalanceFactory loadbalanceFactory(LoadBalanceProperty loadBalanceProperty){
       return new LoadbalanceFactory(loadBalanceProperty);
    }
}

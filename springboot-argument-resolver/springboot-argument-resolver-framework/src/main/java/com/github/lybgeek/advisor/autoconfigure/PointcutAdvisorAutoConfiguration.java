package com.github.lybgeek.advisor.autoconfigure;


import com.github.lybgeek.advisor.CustomerPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PointcutAdvisorAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public CustomerPointcutAdvisor customerPointcutAdvisor(){
       return new CustomerPointcutAdvisor();
    }

}

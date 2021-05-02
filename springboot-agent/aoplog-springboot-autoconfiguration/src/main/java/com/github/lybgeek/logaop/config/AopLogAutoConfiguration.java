package com.github.lybgeek.logaop.config;


import com.github.lybgeek.logaop.advice.ServiceLogAdvice;
import com.github.lybgeek.logaop.properties.AopLogProperties;
import com.github.lybgeek.logaop.service.LogService;
import com.github.lybgeek.logaop.service.impl.LogServiceImpl;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties(AopLogProperties.class)
@ConditionalOnProperty(prefix = "servicelog",name = "enabled",havingValue = "true",matchIfMissing = true)
public class AopLogAutoConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    @ConditionalOnMissingBean
    public LogService logService(){
        return new LogServiceImpl(jdbcTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceLogAdvice serviceLogAdvice(){
        return new ServiceLogAdvice(logService());
    }

    @Bean
    @ConditionalOnMissingBean
    public AspectJExpressionPointcutAdvisor serviceLogAspectJExpressionPointcutAdvisor(AopLogProperties aopLogProperties) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(aopLogProperties.getPointcut());
        advisor.setAdvice(serviceLogAdvice());
        return advisor;
    }


}

package com.github.lybgeek.advise.autoconfigure;


import com.github.lybgeek.advise.CorMethodInterceptor;
import com.github.lybgeek.advise.properties.PointcutProperites;
import com.github.lybgeek.cor.CorHandlerInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PointcutProperites.class)
public class AdviseAutoConfiguration {



    @Bean
    @ConditionalOnMissingBean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor(PointcutProperites pointcutProperites, CorHandlerInterceptor corHandlerInterceptor){
        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setExpression(pointcutProperites.getExpression());
        aspectJExpressionPointcutAdvisor.setAdvice(new CorMethodInterceptor(corHandlerInterceptor));
        return aspectJExpressionPointcutAdvisor;
    }


}

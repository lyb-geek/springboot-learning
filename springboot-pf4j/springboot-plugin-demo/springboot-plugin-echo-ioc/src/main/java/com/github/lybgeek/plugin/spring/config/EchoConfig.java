package com.github.lybgeek.plugin.spring.config;


import com.github.lybgeek.plugin.spring.aop.aspect.MethodCostTimeAspect;
import com.github.lybgeek.plugin.spring.aop.interceptor.MethodCostTimeInterceptor;
import com.github.lybgeek.plugin.spring.aop.pointcut.MethodCostTimePointcut;
import com.github.lybgeek.plugin.spring.util.EchoHelpler;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class EchoConfig {


    @Bean
    @ConditionalOnMissingBean
    public EchoHelpler echoHelpler(){
        return new EchoHelpler();
    }


    @Bean
    @ConditionalOnMissingBean
    public DefaultPointcutAdvisor methodCostTimePointcutAdvisor(){
      return new DefaultPointcutAdvisor(new MethodCostTimePointcut(),new MethodCostTimeInterceptor());

    }


//    @Bean
//    @ConditionalOnMissingBean
//    public MethodCostTimeAspect methodCostTimeAspect(){
//       return new MethodCostTimeAspect();
//    }



}

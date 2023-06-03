package com.github.lybgeek.scope.autoconfigure;


import com.github.lybgeek.scope.RefreshBeanScope;
import com.github.lybgeek.scope.context.RefreshBeanScopeHolder;
import com.github.lybgeek.scope.register.RefreshBeanScopeDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshBeanScopeAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public static RefreshBeanScopeDefinitionRegistryPostProcessor refreshBeanScopeDefinitionRegistryPostProcessor (){
        return new RefreshBeanScopeDefinitionRegistryPostProcessor();
    }


    @Bean
    @ConditionalOnMissingBean
    public RefreshBeanScopeHolder refreshBeanScopeHolder(DefaultListableBeanFactory beanFactory){

        return new RefreshBeanScopeHolder(beanFactory);
    }
}

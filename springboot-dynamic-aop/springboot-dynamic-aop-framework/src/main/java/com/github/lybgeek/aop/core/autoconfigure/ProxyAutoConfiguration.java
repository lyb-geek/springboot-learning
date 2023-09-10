package com.github.lybgeek.aop.core.autoconfigure;


import com.github.lybgeek.aop.core.actuate.ProxyMetaDefinitionControllerEndPoint;
import com.github.lybgeek.aop.core.config.ProxyProperties;
import com.github.lybgeek.aop.core.locator.ProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.locator.listener.ProxyMetaDefinitionChangeListener;
import com.github.lybgeek.aop.core.locator.repository.ProxyMetaDefinitionRepository;
import com.github.lybgeek.aop.core.locator.repository.impl.InMemoryProxyMetaDefinitionRepository;
import com.github.lybgeek.aop.core.locator.support.CompositeProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.locator.support.PropertiesProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.plugin.AopPluginFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

import static com.github.lybgeek.aop.core.config.ProxyProperties.PREFIX;

@Configuration
@EnableConfigurationProperties(ProxyProperties.class)
@ConditionalOnProperty(prefix = PREFIX,name = "enabled",havingValue = "true",matchIfMissing = true)
public class ProxyAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public PropertiesProxyMetaDefinitionLocator propertiesProxyMetaDefinitionLocator(ProxyProperties properties){
       return new PropertiesProxyMetaDefinitionLocator(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InMemoryProxyMetaDefinitionRepository inMemoryProxyMetaDefinitionRepository(){
        return new InMemoryProxyMetaDefinitionRepository();
    }

    @Bean
    @Primary
    public ProxyMetaDefinitionLocator proxyMetaDefinitionLocator(List<ProxyMetaDefinitionLocator> proxyMetaDefinitionLocators){
       return new CompositeProxyMetaDefinitionLocator(proxyMetaDefinitionLocators);
    }


    @Bean
    @ConditionalOnMissingBean
    public AopPluginFactory aopPluginFactory(ProxyMetaDefinitionLocator proxyMetaDefinitionLocator, DefaultListableBeanFactory defaultListableBeanFactory){
        return new AopPluginFactory(proxyMetaDefinitionLocator,defaultListableBeanFactory);

    }

    @Bean
    @ConditionalOnMissingBean
    public ProxyMetaDefinitionChangeListener proxyMetaDefinitionChangeListener(AopPluginFactory aopPluginFactory){
        return new ProxyMetaDefinitionChangeListener(aopPluginFactory);
    }



    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = PREFIX,name = "global-advisor-enabled",havingValue = "true",matchIfMissing = true)
    public AspectJExpressionPointcutAdvisor globalAspectJExpressionPointcutAdvisor(ProxyProperties properties){
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(properties.getGlobalPointcut());
        advisor.setAdvice((MethodBeforeAdvice) (method, args, target) -> {});
        return advisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProxyMetaDefinitionControllerEndPoint proxyMetaDefinitionControllerEndPoint(ProxyMetaDefinitionRepository proxyMetaDefinitionRepository){
       return new ProxyMetaDefinitionControllerEndPoint(proxyMetaDefinitionRepository);
    }



}

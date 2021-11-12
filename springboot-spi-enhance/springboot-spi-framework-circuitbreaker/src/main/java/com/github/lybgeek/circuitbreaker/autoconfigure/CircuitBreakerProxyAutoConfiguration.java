package com.github.lybgeek.circuitbreaker.autoconfigure;


import com.github.lybgeek.circuitbreaker.fallback.exception.DefaultFallBackExceptionHandler;
import com.github.lybgeek.circuitbreaker.spi.factory.CircuitBreakerExtensionFactory;
import com.github.lybgeek.circuitbreaker.spi.factory.CircuitBreakerInstanceInitializingSingleton;
import com.github.lybgeek.circuitbreaker.spi.proxy.CircuitBreakerProxyFactory;
import com.github.lybgeek.circuitbreaker.spi.proxy.ProxyFactory;
import com.github.lybgeek.spring.interceptor.autoconfigure.InterceptorAutoConfiguration;
import com.github.lybgeek.spring.interceptor.handler.InterceptorHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(InterceptorAutoConfiguration.class)
@ComponentScan(basePackageClasses = DefaultFallBackExceptionHandler.class)
public class CircuitBreakerProxyAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public ProxyFactory proxyFactory(){
        return new CircuitBreakerProxyFactory();
    }


    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerExtensionFactory circuitBreakerExtensionFactory(){
        CircuitBreakerExtensionFactory circuitBreakerExtensionFactory = new CircuitBreakerExtensionFactory(proxyFactory());
        return circuitBreakerExtensionFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerInstanceInitializingSingleton circuitBreakerInstanceInitializingSingleton(final InterceptorHandler interceptorHandler){
        return new CircuitBreakerInstanceInitializingSingleton(circuitBreakerExtensionFactory(),interceptorHandler);
    }
}

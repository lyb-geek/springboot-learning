package com.github.lybgeek.circuitbreaker.spi.factory;


import com.github.lybgeek.circuitbreaker.annotation.CircuitBreakerActivate;
import com.github.lybgeek.spring.interceptor.handler.InterceptorHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class CircuitBreakerInstanceInitializingSingleton implements SmartInitializingSingleton, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    private CircuitBreakerExtensionFactory circuitBreakerExtensionFactory;

    private InterceptorHandler interceptorHandler;


    public CircuitBreakerInstanceInitializingSingleton(CircuitBreakerExtensionFactory circuitBreakerExtensionFactory, InterceptorHandler interceptorHandler) {
        this.circuitBreakerExtensionFactory = circuitBreakerExtensionFactory;
        this.interceptorHandler = interceptorHandler;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        circuitBreakerExtensionFactory.setChain(interceptorHandler.getInterceptorChain());
        beanFactory.getBeansWithAnnotation(CircuitBreakerActivate.class)
                .forEach((beanName,bean)->{
                    if(beanFactory.containsBeanDefinition(beanName)){
                        beanFactory.removeBeanDefinition(beanName);
                        CircuitBreakerActivate circuitBreakerActivate = AnnotationUtils.findAnnotation(bean.getClass(),CircuitBreakerActivate.class);
                        beanFactory.registerSingleton(beanName,circuitBreakerExtensionFactory.getCircuitBreakerExtension(circuitBreakerActivate.spiKey(),bean.getClass().getInterfaces()[0]));
                    }
                });

    }
}

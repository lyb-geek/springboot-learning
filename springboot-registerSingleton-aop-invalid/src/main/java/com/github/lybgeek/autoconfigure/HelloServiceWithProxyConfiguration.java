package com.github.lybgeek.autoconfigure;


import com.github.lybgeek.model.HelloServiceProperties;
import com.github.lybgeek.service.HelloService;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloServiceWithProxyConfiguration implements BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    @Autowired
    private AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;
        HelloServiceProperties properties = new HelloServiceProperties();
        properties.setBeanName("helloService");
        HelloService helloServicePrxoy = (HelloService) annotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization(new HelloService(properties), "helloService$$Prxoy");
        defaultListableBeanFactory.registerSingleton(properties.getBeanName(),helloServicePrxoy);

    }
}

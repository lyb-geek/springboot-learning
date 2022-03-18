package com.github.lybgeek.autoconfigure;


import com.github.lybgeek.model.HelloServiceProperties;
import com.github.lybgeek.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class HelloServiceConfiguration implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanDefinition.setBeanClass(HelloService.class);
        HelloServiceProperties properties = new HelloServiceProperties();
        properties.setBeanName("helloService");
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,properties);
        defaultListableBeanFactory.registerBeanDefinition(properties.getBeanName(),beanDefinition);

    }
}

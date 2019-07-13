package com.github.lybgeek.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    private static ConfigurableApplicationContext configurableContext ;
    private static BeanDefinitionRegistry beanDefinitionRegistry ;

    /**
     * 注册bean
     *
     */
    public static void registerBean(String beanId,String className) {
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder =
        BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // register the bean
        beanDefinitionRegistry.registerBeanDefinition(beanId,beanDefinition);
    }

    /**
     * 移除bean
     */
    public static void unregisterBean(String beanId){
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 获取bean
     */
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        configurableContext = (ConfigurableApplicationContext) context;
        beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
    }
}

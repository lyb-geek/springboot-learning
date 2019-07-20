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

/**
 * springApplicationContext工具
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private  ApplicationContext context;
    private  ConfigurableApplicationContext configurableContext;
    private  BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 注册bean
     * @param beanId 所注册bean的id
     * @param className bean的className，
     *                     三种获取方式：1、直接书写，如：com.mvc.entity.User
     *                                   2、User.class.getName
     *                                   3.user.getClass().getName()
     */
    public  void registerBean(String beanId,String className) {
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
     * @param beanId bean的id
     */
    public  void unregisterBean(String beanId){
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 获取bean
     * @param name bean的id
     * @param <T>
     * @return
     */
    public  <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        configurableContext = (ConfigurableApplicationContext) context;
        beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
    }
}

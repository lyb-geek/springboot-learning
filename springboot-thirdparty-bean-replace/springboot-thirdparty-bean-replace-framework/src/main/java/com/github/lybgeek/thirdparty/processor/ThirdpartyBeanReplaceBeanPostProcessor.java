package com.github.lybgeek.thirdparty.processor;


import com.github.lybgeek.thirdparty.property.ThirdpartyBeanReplaceProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThirdpartyBeanReplaceBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    private AtomicBoolean isAlreadyReplace = new AtomicBoolean(false);

    private final ThirdpartyBeanReplaceProperty thirdpartyBeanReplaceProperty;

    public ThirdpartyBeanReplaceBeanPostProcessor(ThirdpartyBeanReplaceProperty thirdpartyBeanReplaceProperty) {
        this.thirdpartyBeanReplaceProperty = thirdpartyBeanReplaceProperty;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if(thirdpartyBeanReplaceProperty.isBeanReplace() && !CollectionUtils.isEmpty(thirdpartyBeanReplaceProperty.getReplaceBeans()) && !isAlreadyReplace.get()){
            thirdpartyBeanReplaceProperty.getReplaceBeans().forEach(thirdpartyReplaceBeanHolder -> {
                defaultListableBeanFactory.removeBeanDefinition(thirdpartyReplaceBeanHolder.getReplaceBeanName());
                BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
                beanDefinition.setBeanClassName(thirdpartyReplaceBeanHolder.getReplaceBeanClassName());
                defaultListableBeanFactory.registerBeanDefinition(thirdpartyReplaceBeanHolder.getReplaceBeanName(),beanDefinition);
                logger.info("replace bean:{} to bean:{}",thirdpartyReplaceBeanHolder.getReplaceBeanName(),thirdpartyReplaceBeanHolder.getReplaceBeanClassName());
                isAlreadyReplace.set(true);
            });
        }

        return SmartInstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);

    }




}

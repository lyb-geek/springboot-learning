package com.github.lybgeek.spring.bean;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.spring.parse.ComponentLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;


@Slf4j
@ComponentScan
public class AutoComponentBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        registerBeanDefinition(registry);

    }

    @SneakyThrows
    private void registerBeanDefinition(BeanDefinitionRegistry registry) {
        Set<String> componentClassNames = ComponentLoader.load();
        if(CollectionUtil.isNotEmpty(componentClassNames)){
            log.info("load componentClassNames -> {} from 【{}】",componentClassNames,ComponentLoader.COMPONENTS_RESOURCE_LOCATION);
            for (String componentClassName : componentClassNames) {
                Class componentClass = Class.forName(componentClassName);
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(componentClass).getBeanDefinition();
                BeanNameGenerator componentBeanNameGenerator = new AnnotationBeanNameGenerator();
                String beanName = componentBeanNameGenerator.generateBeanName(beanDefinition, registry);
                registry.registerBeanDefinition(beanName,beanDefinition);

            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

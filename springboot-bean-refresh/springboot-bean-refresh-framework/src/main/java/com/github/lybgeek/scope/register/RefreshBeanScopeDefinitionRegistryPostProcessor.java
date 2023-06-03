package com.github.lybgeek.scope.register;


import com.github.lybgeek.scope.RefreshBeanScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import static com.github.lybgeek.scope.constant.RefreshBeanScopeConstant.SCOPE_NAME;

public class RefreshBeanScopeDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            beanFactory.registerScope(SCOPE_NAME,new RefreshBeanScope());
    }
}

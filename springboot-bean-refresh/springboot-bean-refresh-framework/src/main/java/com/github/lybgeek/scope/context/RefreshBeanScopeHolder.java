package com.github.lybgeek.scope.context;


import com.github.lybgeek.scope.event.RefreshBeanEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.lybgeek.scope.constant.RefreshBeanScopeConstant.SCOPE_NAME;

@RequiredArgsConstructor
public class RefreshBeanScopeHolder implements ApplicationContextAware {
    
    private final DefaultListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;
    
    
    public List<String> refreshBean(){
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        List<String> refreshBeanDefinitionNames = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if(SCOPE_NAME.equals(beanDefinition.getScope())){
                beanFactory.destroyScopedBean(beanDefinitionName);
                beanFactory.getBean(beanDefinitionName);
                refreshBeanDefinitionNames.add(beanDefinitionName);
                applicationContext.publishEvent(new RefreshBeanEvent(beanDefinitionName));
            }
        }

        return Collections.unmodifiableList(refreshBeanDefinitionNames);
        
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

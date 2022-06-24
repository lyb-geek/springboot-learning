package com.github.lybgeek.pipeline.spring.scan;


import com.github.lybgeek.pipeline.spring.factory.ComsumePipelineFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

public class PipelineClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public PipelineClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }


    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            String className = beanDefinition.getBeanClassName();
            beanDefinition.getPropertyValues().addPropertyValue("pipelineServiceClz",className);
            beanDefinition.setBeanClass(ComsumePipelineFactoryBean.class);

        }

        return beanDefinitionHolders;

    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}

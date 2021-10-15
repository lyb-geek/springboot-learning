package com.github.lybgeek.spring.spi.scan;


import com.github.lybgeek.spi.anotatation.Activate;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;

public class ActivateClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {


    public ActivateClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }


    @SneakyThrows
    @Override
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        super.registerBeanDefinition(definitionHolder, registry);
        Class clz = Class.forName(definitionHolder.getBeanDefinition().getBeanClassName());
        Activate activate = AnnotationUtils.findAnnotation(clz,Activate.class);
        if(ObjectUtils.isNotEmpty(activate) && StringUtils.isNotBlank(activate.value())){
            //处理当activate.value()为占位符，形如 ${activate.value}
            String activateName = getEnvironment().resolvePlaceholders(activate.value());
            registry.registerBeanDefinition(activateName,definitionHolder.getBeanDefinition());
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata()
                .hasAnnotation(Activate.class.getName());
    }


}

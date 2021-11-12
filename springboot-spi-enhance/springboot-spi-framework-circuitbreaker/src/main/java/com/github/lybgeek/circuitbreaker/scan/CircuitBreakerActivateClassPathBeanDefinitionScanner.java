package com.github.lybgeek.circuitbreaker.scan;


import com.github.lybgeek.circuitbreaker.annotation.CircuitBreakerActivate;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class CircuitBreakerActivateClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {


    public CircuitBreakerActivateClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }


    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return super.isCandidateComponent(beanDefinition) && beanDefinition.getMetadata().hasAnnotation(CircuitBreakerActivate.class.getName());
    }


}

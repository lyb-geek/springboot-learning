package com.github.lybgeek.circuitbreaker.register;


import com.github.lybgeek.circuitbreaker.annotation.CircuitBreakerActivate;
import com.github.lybgeek.circuitbreaker.annotation.EnableSpiCircuitBreaker;
import com.github.lybgeek.circuitbreaker.scan.CircuitBreakerActivateClassPathBeanDefinitionScanner;
import com.github.lybgeek.spring.spi.util.SpiBeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class SpiCircuitBreakerRegister implements ImportBeanDefinitionRegistrar{


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> basePackages = SpiBeanUtils.getBasePackages(importingClassMetadata, EnableSpiCircuitBreaker.class.getCanonicalName());
        String[] packages = {};

        CircuitBreakerActivateClassPathBeanDefinitionScanner scanner = new CircuitBreakerActivateClassPathBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new AnnotationTypeFilter(CircuitBreakerActivate.class));
        scanner.scan(basePackages.toArray(packages));
    }

}

package com.github.lybgeek.spring.spi.register;


import com.github.lybgeek.spring.spi.annatation.EnableSpi;
import com.github.lybgeek.spring.spi.util.SpiBeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

public class SpiRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> basePackages = SpiBeanUtils.getBasePackages(importingClassMetadata,EnableSpi.class.getCanonicalName());
        String[] packages = {};
        SpiBeanUtils.registerActivateInstances(registry,environment,basePackages.toArray(packages));
    }



    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}



package com.github.lybgeek.spring.spi.register;


import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spring.spi.annatation.EnableSpi;
import com.github.lybgeek.spring.spi.scan.ActivateClassPathBeanDefinitionScanner;
import com.github.lybgeek.spring.spi.util.SpiBeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpiRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> basePackages = this.getBasePackages(importingClassMetadata);
        String[] packages = {};
        SpiBeanUtils.registerActivateInstances(registry,environment,basePackages.toArray(packages));
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(EnableSpi.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();
        fillBasePackages(basePackages,attributes,"value");
        fillBasePackages(basePackages,attributes,"basePackages");

        if(ObjectUtil.isNotNull(attributes.get("basePackageClasses"))) {
            for (Class<?> clazz : (Class[]) attributes.get("basePackageClasses")) {
                basePackages.add(ClassUtils.getPackageName(clazz));
            }
        }

        if (basePackages.isEmpty()) {
            basePackages.add(
                    ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }

    private void fillBasePackages(Set<String> basePackages,Map<String, Object> attributes,String key){
        if(ObjectUtil.isNotNull(attributes.get(key))) {
            for (String pkg : (String[]) attributes.get(key)) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}



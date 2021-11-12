package com.github.lybgeek.spring.spi.util;


import cn.hutool.core.util.ObjectUtil;
import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spi.anotatation.SPI;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import com.github.lybgeek.spring.spi.scan.ActivateClassPathBeanDefinitionScanner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.util.*;

@Slf4j
public final class SpiBeanUtils {

    private SpiBeanUtils(){

    }


    public static List<Class<?>> getSpiInterfaces(DefaultListableBeanFactory beanFactory,Class spiClz){
        List<Class<?>> spiInterfaces = new ArrayList<>();
        for (Class spiInterface : spiClz.getInterfaces()) {
            if(spiInterface.isAnnotationPresent(SPI.class)){
                spiInterfaces.add(spiInterface);
            }
        }
        return spiInterfaces;
    }

    public static void registerSpiInterfaces(DefaultListableBeanFactory beanFactory,Object bean) {
        List<Class<?>> spiInterfaces = getSpiInterfaces(beanFactory,bean.getClass());
        for (Class<?> spiInterface : spiInterfaces) {
            SPI spi = spiInterface.getAnnotation(SPI.class);
            String defaultBeanName = spi.value();
            if(StringUtils.isNotBlank(defaultBeanName)){
                Object spiObj = ExtensionLoader.getExtensionLoader(spiInterface).getActivate(defaultBeanName);
                if(bean.getClass().isAssignableFrom(spiObj.getClass())){
                    String spiInterfaceBeanName = StringUtils.uncapitalize(spiInterface.getSimpleName());
                    beanFactory.registerSingleton(spiInterfaceBeanName,bean);
                    log.info("register spi {} to spring success ,defalut spi bean is {}!",spiInterfaceBeanName,bean.getClass().getName());
                }
            }
        }
    }

    public static void registerActivateInstances(BeanDefinitionRegistry registry, Environment environment, String... basePackages) {
        ActivateClassPathBeanDefinitionScanner activateClassPathBeanDefinitionScanner = new ActivateClassPathBeanDefinitionScanner(registry);
        activateClassPathBeanDefinitionScanner.setEnvironment(environment);
        activateClassPathBeanDefinitionScanner.addIncludeFilter(new AnnotationTypeFilter(Activate.class));
        activateClassPathBeanDefinitionScanner.scan(basePackages);

    }


    public static Set<String> getBasePackages(AnnotationMetadata importingClassMetadata,String annotationName) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(annotationName);

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

    private static void fillBasePackages(Set<String> basePackages,Map<String, Object> attributes,String key){
        if(ObjectUtil.isNotNull(attributes.get(key))) {
            for (String pkg : (String[]) attributes.get(key)) {
                if (org.springframework.util.StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }
    }





}

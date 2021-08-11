package com.github.lybgeek.spi.framework.spring;


import cn.hutool.core.map.MapUtil;
import com.github.lybgeek.spi.framework.anotation.EnableSpi;
import com.github.lybgeek.spi.framework.anotation.Spi;
import com.github.lybgeek.spi.framework.factory.SpiFactory;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Deprecated
public class SpiInitializingSingleton implements SmartInitializingSingleton,BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void afterSingletonsInstantiated() {
        registerSingleton();

    }


    private void registerSingleton() {
        Class<?> spiInterface = getSpiInterface();
        if(spiInterface != null){
            Map<String,?> spiMap = new SpiFactory().getSpiMap(spiInterface);
            if(MapUtil.isNotEmpty(spiMap)){
                spiMap.forEach((beanName,bean) -> {
                    beanFactory.registerSingleton(beanName,bean);
                });
            }

        }
    }

    private Class<?> getSpiInterface() {
        List<String> basePackages = getBasePackages();
        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<?>> spiClasses = reflections.getTypesAnnotatedWith(Spi.class);
            if(!CollectionUtils.isEmpty(spiClasses)){
                for (Class<?> spiClass : spiClasses) {
                    if(spiClass.isInterface()){
                        return spiClass;
                    }
                }
            }
        }

        return null;
    }


    private List<String> getBasePackages() {
        EnableSpi enableSpi = AnnotationUtils.findAnnotation(findEnableSpiClz(),EnableSpi.class);
        List<String> basePackages = Arrays.asList(enableSpi.basePackages());
        if(CollectionUtils.isEmpty(basePackages)){
            basePackages = AutoConfigurationPackages.get(beanFactory);
        }
        return basePackages;
    }


    private Class findEnableSpiClz(){
        Map<String, Object> annotatedBeans = beanFactory.getBeansWithAnnotation(EnableSpi.class);
        Assert.notEmpty(annotatedBeans,"@EnableSpi is not bundle");
        return  annotatedBeans.values().toArray()[0].getClass();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }
}

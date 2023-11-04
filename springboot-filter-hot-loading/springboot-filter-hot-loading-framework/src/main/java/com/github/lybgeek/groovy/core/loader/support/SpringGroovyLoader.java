package com.github.lybgeek.groovy.core.loader.support;


import com.github.lybgeek.groovy.core.compiler.DynamicCodeCompiler;
import com.github.lybgeek.groovy.core.event.GroovyBeanRegisterEvent;
import com.github.lybgeek.groovy.core.loader.GroovyLoader;
import com.github.lybgeek.groovy.core.registry.GroovyRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public final class SpringGroovyLoader<T> implements GroovyLoader<T>, ApplicationContextAware {

    private final  ConcurrentMap<String, Long> groovyClassLastModified = new ConcurrentHashMap<>();

    private final DynamicCodeCompiler compiler;

    private final DefaultListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public SpringGroovyLoader(DynamicCodeCompiler compiler, DefaultListableBeanFactory beanFactory) {
        this.compiler = compiler;
        this.beanFactory = beanFactory;
    }

    @Override
    public boolean putObject(File file) {
        try {
            removeCurBeanIfFileChange(file);
            return registerGroovyBean(file);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Error loading object! Continuing. file=" + file, e);
        }

        return false;
    }

    private void removeCurBeanIfFileChange(File file) {
        String sName = file.getAbsolutePath();
        if (groovyClassLastModified.get(sName) != null
                && (file.lastModified() != groovyClassLastModified.get(sName))) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Reloading object " + sName);
            if(beanFactory.containsBean(sName)){
                beanFactory.removeBeanDefinition(sName);
                beanFactory.destroySingleton(sName);
            }
        }
    }

    private boolean registerGroovyBean(File file) throws Exception {
        String sName = file.getAbsolutePath();
        boolean containsBean = beanFactory.containsBean(sName);
        if(!containsBean){
            Class<?> clazz = compiler.compile(file);
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                return registerBean(sName,clazz, file.lastModified());
            }
        }
        return false;
    }

    private boolean registerBean(String beanName, Class beanClz,long lastModified) {
        try {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            beanDefinition.setBeanClass(beanClz);
            beanDefinition.setSource("groovyCompile");
            beanFactory.registerBeanDefinition(beanName,beanDefinition);
            BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
            String aliasBeanName = beanNameGenerator.generateBeanName(beanDefinition, beanFactory);
            beanFactory.registerAlias(beanName,aliasBeanName);
            groovyClassLastModified.put(beanName, lastModified);

            GroovyBeanRegisterEvent groovyBeanRegisterEvent = GroovyBeanRegisterEvent.builder()
                            .beanClz(beanClz).beanName(beanName).aliasBeanName(aliasBeanName).build();
            applicationContext.publishEvent(groovyBeanRegisterEvent);
            return true;
        } catch (BeanDefinitionStoreException e) {
           log.error(">>>>>>>>>>>>>>>>>>>>>>registerBean fail,cause:" + e.getMessage(),e);
        }
        return false;
    }



    @Override
    public List<T> putObjectsForClasses(String[] classNames) throws Exception {
        List<T> newObjects = new ArrayList<>();
        for (String className : classNames) {
            newObjects.add(putObjectForClassName(className));
        }
        return Collections.unmodifiableList(newObjects);
    }

    @Override
    public T putObjectForClassName(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        registerBean(className, clazz, System.currentTimeMillis());
        return (T) beanFactory.getBean(className);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

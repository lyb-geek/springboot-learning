/*
 * Copyright (C) 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lybgeek.plugin.spring.injector;

import com.github.lybgeek.plugin.spring.delegete.SpringExtensionFactoryDelegete;
import com.github.lybgeek.plugin.spring.delegete.SpringPluginManagerDelegete;
import org.pf4j.ExtensionFactory;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Decebal Suiu
 */
public class SpringExtensionsInjector {

    private static final Logger log = LoggerFactory.getLogger(SpringExtensionsInjector.class);

    protected final SpringPluginManager springPluginManager;
    protected final DefaultListableBeanFactory beanFactory;


    public SpringExtensionsInjector(SpringPluginManager springPluginManager, DefaultListableBeanFactory beanFactory) {
        this.springPluginManager = springPluginManager;
        this.beanFactory = beanFactory;
    }



    public void injectExtensions() {
        // add extensions from classpath (non plugin)
        Set<String> extensionClassNames = springPluginManager.getExtensionClassNames(null);
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = getClass().getClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }

        // add extensions for each started plugin
        List<PluginWrapper> startedPlugins = springPluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {
            registerPlugin2Spring(plugin);
        }
    }


    public void removePluginFromSpring(Class<?> extensionClass,String pluginId) {
        try {
            Object bean = springPluginManager.getApplicationContext().getBean(pluginId, extensionClass);
            springPluginManager.getApplicationContext().getAutowireCapableBeanFactory().destroyBean(bean);
        } catch (Exception e) {
            log.error("Destroy bean error",e);
        }

    }

    private void registerPlugin2Spring(PluginWrapper plugin) {
        Set<String> extensionClassNames;
        log.debug("Registering extensions of the plugin '{}' as beans", plugin.getPluginId());
        extensionClassNames = springPluginManager.getExtensionClassNames(plugin.getPluginId());
        for (String extensionClassName : extensionClassNames) {
            try {
                log.debug("Register extension '{}' as bean", extensionClassName);
                Class<?> extensionClass = plugin.getPluginClassLoader().loadClass(extensionClassName);
                registerExtension(extensionClass, plugin.getPluginId());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Register an extension as bean.
     * Current implementation register extension as singleton using {@code beanFactory.registerSingleton()}.
     * The extension instance is created using {@code pluginManager.getExtensionFactory().create(extensionClass)}.
     * The bean name is the extension class name.
     * Override this method if you wish other register strategy.
     */
    protected void registerExtension(Class<?> extensionClass,String...pluginIds) {
        Map<String, ?> extensionBeanMap = springPluginManager.getApplicationContext().getBeansOfType(extensionClass);
        if (extensionBeanMap.isEmpty()) {
            registerPlugin2Spring(extensionClass, pluginIds);
        } else {
            log.debug("Bean registeration aborted! Extension '{}' already existed as bean!", extensionClass.getName());
        }
    }

    private void registerPlugin2Spring(Class<?> extensionClass, String[] pluginIds) {
        Object extension = springPluginManager.getExtensionFactory().create(extensionClass);

        String pluginBeanName = getBeanName(extensionClass);
        beanFactory.registerSingleton(pluginBeanName, extension);

        if(pluginIds != null && pluginIds.length > 0){
            beanFactory.registerAlias(pluginBeanName, pluginIds[0]);
        }
    }

    private String getBeanName(Class<?> extensionClass) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(extensionClass);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        AnnotationBeanNameGenerator annotationBeanNameGenerator = new AnnotationBeanNameGenerator();
        return annotationBeanNameGenerator.generateBeanName(beanDefinition, beanFactory);
    }

}

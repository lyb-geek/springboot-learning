package com.github.lybgeek.plugin.spring.delegete;


import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginManager;
import org.pf4j.spring.SpringExtensionFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@Slf4j
public class SpringExtensionFactoryDelegete extends SpringExtensionFactory {
    public SpringExtensionFactoryDelegete(PluginManager pluginManager) {
        super(pluginManager);
    }

    public SpringExtensionFactoryDelegete(PluginManager pluginManager, boolean autowire) {
        super(pluginManager, autowire);
    }

    @Override
    public <T> Optional<ApplicationContext> getApplicationContextBy(Class<T> extensionClass) {
        return super.getApplicationContextBy(extensionClass);
    }

    @Override
    protected <T> T createWithSpring(Class<T> extensionClass, ApplicationContext applicationContext) {
        final AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        log.debug("Instantiate extension class '" + nameOf(extensionClass) + "' ");
        return beanFactory.createBean(extensionClass);
    }


    private <T> String nameOf(final Class<T> clazz) {
        return clazz.getName();
    }
}

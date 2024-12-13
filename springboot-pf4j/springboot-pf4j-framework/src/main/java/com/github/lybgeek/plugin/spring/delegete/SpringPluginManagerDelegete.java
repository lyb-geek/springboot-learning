package com.github.lybgeek.plugin.spring.delegete;


import com.github.lybgeek.plugin.spring.injector.SpringExtensionsInjector;
import org.pf4j.*;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.nio.file.Path;
import java.util.List;

public class SpringPluginManagerDelegete extends SpringPluginManager {



    public SpringPluginManagerDelegete() {
    }

    public SpringPluginManagerDelegete(Path... pluginsRoots) {
        super(pluginsRoots);
    }

    public SpringPluginManagerDelegete(List<Path> pluginsRoots) {
        super(pluginsRoots);
    }


    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new SpringExtensionFactoryDelegete(this);
    }


    @Override
    protected PluginDescriptorFinder createPluginDescriptorFinder() {
        return new CompoundPluginDescriptorFinder()
                .add(new PropertiesPluginDescriptorFinder())
                .add(new ManifestPluginDescriptorFinder());
    }


    public void removeExtensions(Class<?> extensionClass,String pluginId) {
        boolean isSuccess = unloadPlugin(pluginId);
        if(isSuccess){
            SpringExtensionsInjector extensionsInjector = getSpringExtensionsInjector();
            if(extensionsInjector != null){
                extensionsInjector.removePluginFromSpring(extensionClass,pluginId);
            }
        }

    }


    @Override
    public void init() {
        loadPlugins();
        startPlugins();

        SpringExtensionsInjector extensionsInjector = getSpringExtensionsInjector();
        if(extensionsInjector != null){
            extensionsInjector.injectExtensions();
        }

    }

    private SpringExtensionsInjector getSpringExtensionsInjector(){
        AutowireCapableBeanFactory autowireCapableBeanFactory = getApplicationContext().getAutowireCapableBeanFactory();
        if(autowireCapableBeanFactory instanceof DefaultListableBeanFactory){
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) autowireCapableBeanFactory;
            return new SpringExtensionsInjector(this, beanFactory);
        }
        return null;
    }


}

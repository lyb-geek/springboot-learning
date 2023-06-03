package com.github.lybgeek.refresh.test.monitor;

import com.github.lybgeek.scope.context.RefreshBeanScopeHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.io.File;
import java.util.List;


@Slf4j
public class ConfigPropertyFileAlterationListener extends FileAlterationListenerAdaptor {


    private ApplicationContext applicationContext;

    public ConfigPropertyFileAlterationListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        super.onDirectoryCreate(directory);
    }

    @Override
    public void onDirectoryChange(File directory) {
       super.onDirectoryChange(directory);

    }

    @Override
    public void onDirectoryDelete(File directory) {
        super.onDirectoryDelete(directory);
    }

    @Override
    public void onFileCreate(File file) {
        super.onFileCreate(file);
    }

    @Override
    public void onFileChange(File file) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> Monitor PropertyFile with path --> {}",file.getName());
        refreshConfig(file);

    }

    @Override
    public void onFileDelete(File file) {
        super.onFileDelete(file);

    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }

    @SneakyThrows
    private void refreshConfig(File file){
        ConfigurableEnvironment environment = applicationContext.getBean(ConfigurableEnvironment.class);
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySourceList = propertySourceLoader.load(file.getAbsolutePath(), applicationContext.getResource("file:"+file.getAbsolutePath()));
        for (PropertySource<?> propertySource : propertySources) {
           if(propertySource.getName().contains(file.getName())){
               propertySources.replace(propertySource.getName(),propertySourceList.get(0));
           }
        }


        RefreshBeanScopeHolder refreshBeanScopeHolder = applicationContext.getBean(RefreshBeanScopeHolder.class);
        List<String> strings = refreshBeanScopeHolder.refreshBean();
        log.info(">>>>>>>>>>>>>>> refresh Bean :{}",strings);


    }

}

package com.github.lybgeek.groovy.autoconfigure;

import com.github.lybgeek.groovy.core.GroovyFileMonitorManager;
import com.github.lybgeek.groovy.core.compiler.DynamicCodeCompiler;
import com.github.lybgeek.groovy.core.compiler.support.GroovyCompiler;
import com.github.lybgeek.groovy.core.loader.support.SpringGroovyLoader;
import com.github.lybgeek.groovy.properties.GroovyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Configuration
@EnableConfigurationProperties(GroovyProperties.class)
public class GroovyAutoConfiguration implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ExecutorService service = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r);
        thread.setName("groovyFileManager-thread");
        return thread;
    });

    @Bean
    @ConditionalOnMissingBean
    public SpringGroovyLoader springGroovyLoader(DefaultListableBeanFactory beanFactory){
        DynamicCodeCompiler dynamicCodeCompiler = new GroovyCompiler();
        return new SpringGroovyLoader(dynamicCodeCompiler,beanFactory);
    }

    @Bean("springGroovyFileManager")
    @ConditionalOnMissingBean
    public GroovyFileMonitorManager springGroovyFileManager(GroovyProperties groovyProperties, SpringGroovyLoader springGroovyLoader){
         return new GroovyFileMonitorManager(groovyProperties,springGroovyLoader);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        GroovyFileMonitorManager springGroovyFileManager = applicationContext.getBean("springGroovyFileManager", GroovyFileMonitorManager.class);
        try {
            springGroovyFileManager.init();
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>> springGroovyFileManager init fail",e);
        }
    }
}

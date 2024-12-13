package com.github.lybgeek.plugin.autoconfigure;


import com.github.lybgeek.plugin.PluginManagerWapper;
import com.github.lybgeek.plugin.properties.PluginProperties;
import com.github.lybgeek.plugin.spring.delegete.SpringPluginManagerDelegete;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties(PluginProperties.class)
public class PluginManagerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SpringPluginManager springPluginManager(PluginProperties pluginProperties, AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator){
        SpringPluginManagerDelegete springPluginManager = new SpringPluginManagerDelegete();
        if(StringUtils.hasText(pluginProperties.getDir())){
            springPluginManager = new SpringPluginManagerDelegete(Paths.get(pluginProperties.getDir()));
        }

        return springPluginManager;
    }


    @Bean
    @ConditionalOnMissingBean
    public PluginManagerWapper pluginManagerWapper(SpringPluginManager springPluginManager){
        return new PluginManagerWapper(springPluginManager);
    }
}

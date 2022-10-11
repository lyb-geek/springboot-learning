package com.github.lybgeek.ds.switchover.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

@Configuration
public class BeanDefintionOverridedEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        String key = "spring.main.allow-bean-definition-overriding";
        MutablePropertySources m = environment.getPropertySources();
        Properties p = new Properties();
        p.put(key, true);
        m.addFirst(new PropertiesPropertySource("commonDataProperties", p));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
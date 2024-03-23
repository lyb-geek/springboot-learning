package com.github.lybgeek.config.binder.support;


import com.github.lybgeek.config.binder.PropertyRebinder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public abstract class AbstractPropertyRebinder implements EnvironmentAware, ApplicationContextAware, PropertyRebinder {

    protected Environment environment;

    protected ApplicationContext context;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

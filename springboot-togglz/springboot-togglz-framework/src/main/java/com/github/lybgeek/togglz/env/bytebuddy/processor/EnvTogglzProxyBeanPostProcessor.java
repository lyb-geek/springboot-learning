package com.github.lybgeek.togglz.env.bytebuddy.processor;


import com.github.lybgeek.togglz.env.annotation.EnvTogglz;
import com.github.lybgeek.togglz.env.annotation.util.AnnotationHelper;
import com.github.lybgeek.togglz.env.bytebuddy.factory.EnvTogglzProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

@Slf4j
public class EnvTogglzProxyBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        EnvTogglz envTogglz = getEnvTogglz(bean);
        if(envTogglz != null){
            String activeEnv = applicationContext.getEnvironment().resolvePlaceholders(envTogglz.activeEnv());
            return EnvTogglzProxyFactory.createProxy(bean,activeEnv);
        }

        return bean;

    }



    private EnvTogglz getEnvTogglz(Object bean) {
        boolean aopProxy = AopUtils.isAopProxy(bean);
        Class targetClz = bean.getClass();
        if(aopProxy){
           targetClz = AopUtils.getTargetClass(bean);
        }

        for (Method declaredMethod : targetClz.getDeclaredMethods()) {
            EnvTogglz envTogglz = AnnotationHelper.getAnnotation(declaredMethod, EnvTogglz.class);
            if(envTogglz != null){
                return envTogglz;
            }
        }

        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}

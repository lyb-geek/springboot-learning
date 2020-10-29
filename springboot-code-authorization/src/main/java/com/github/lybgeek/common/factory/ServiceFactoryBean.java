package com.github.lybgeek.common.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceFactoryBean <T> implements FactoryBean<T> , ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private Class<T> serviceClz;

    private String serviceImplClzName;

    private String secretKey;

    private Object targetObj;

    @Override
    public T getObject() throws Exception {
        return (T) targetObj;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceClz;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        targetObj = ServiceFactory.create(secretKey,serviceImplClzName,applicationContext);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

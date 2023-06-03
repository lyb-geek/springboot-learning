package com.github.lybgeek.scope;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RefreshBeanScope implements Scope {

    private final Map<String,Object> beanMap = new ConcurrentHashMap<>(256);

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(beanMap.containsKey(name)){
            return beanMap.get(name);
        }

        Object bean = objectFactory.getObject();
        beanMap.put(name,bean);
        return bean;
    }

    @Override
    public Object remove(String name) {
        return beanMap.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}

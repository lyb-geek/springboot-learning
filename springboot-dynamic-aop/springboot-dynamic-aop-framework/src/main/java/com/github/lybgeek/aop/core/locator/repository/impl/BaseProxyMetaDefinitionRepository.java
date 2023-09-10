package com.github.lybgeek.aop.core.locator.repository.impl;


import com.github.lybgeek.aop.core.locator.enums.OperateEventEnum;
import com.github.lybgeek.aop.core.locator.event.ProxyMetaDefinitionChangeEvent;
import com.github.lybgeek.aop.core.locator.repository.ProxyMetaDefinitionRepository;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinitionChangeEntity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class BaseProxyMetaDefinitionRepository implements ProxyMetaDefinitionRepository, ApplicationContextAware  {

    protected ApplicationContext applicationContext;


    @Override
    public void save(ProxyMetaDefinition definition) {
        if(saveProxyMetaDefinition(definition)){
            applicationContext.publishEvent(new ProxyMetaDefinitionChangeEvent(this,new ProxyMetaDefinitionChangeEntity(OperateEventEnum.ADD,definition)));
        }

    }

    @Override
    public void delete(String proxyMetaDefinitionId) {
        if(deleteProxyMetaDefinition(proxyMetaDefinitionId)){
            applicationContext.publishEvent(new ProxyMetaDefinitionChangeEvent(this,new ProxyMetaDefinitionChangeEntity(OperateEventEnum.DEL,ProxyMetaDefinition.builder().id(proxyMetaDefinitionId).build())));
        }
    }

    public abstract boolean saveProxyMetaDefinition(ProxyMetaDefinition definition);

    public abstract boolean deleteProxyMetaDefinition(String proxyMetaDefinitionId);



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

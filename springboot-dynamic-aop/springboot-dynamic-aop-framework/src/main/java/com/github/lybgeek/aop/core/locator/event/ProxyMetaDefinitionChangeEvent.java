package com.github.lybgeek.aop.core.locator.event;


import com.github.lybgeek.aop.core.locator.enums.OperateEventEnum;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinitionChangeEntity;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.ObjectUtils;

public class ProxyMetaDefinitionChangeEvent extends ApplicationEvent {

    private final ProxyMetaDefinitionChangeEntity proxyMetaDefinitionChangeEntity;

    public ProxyMetaDefinitionChangeEvent(Object source,ProxyMetaDefinitionChangeEntity proxyMetaDefinitionChangeEntity) {
        super(source);
        this.proxyMetaDefinitionChangeEntity = proxyMetaDefinitionChangeEntity;
    }

    public OperateEventEnum getOperateEventEnum() {
        if(ObjectUtils.isEmpty(proxyMetaDefinitionChangeEntity)){
            return OperateEventEnum.UNKOWN;
        }
        return proxyMetaDefinitionChangeEntity.getOperateEventEnum();
    }

    public ProxyMetaDefinition getProxyMetaDefinition() {
        if(ObjectUtils.isEmpty(proxyMetaDefinitionChangeEntity)){
            return null;
        }
        return proxyMetaDefinitionChangeEntity.getDefinition();
    }
}

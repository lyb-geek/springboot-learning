package com.github.lybgeek.aop.core.locator.listener;


import com.github.lybgeek.aop.core.locator.event.ProxyMetaDefinitionChangeEvent;
import com.github.lybgeek.aop.core.model.ProxyMetaInfo;
import com.github.lybgeek.aop.core.plugin.AopPluginFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class ProxyMetaDefinitionChangeListener {

    private final AopPluginFactory aopPluginFactory;

    @EventListener
    public void listener(ProxyMetaDefinitionChangeEvent proxyMetaDefinitionChangeEvent){
        ProxyMetaInfo proxyMetaInfo = aopPluginFactory.getProxyMetaInfo(proxyMetaDefinitionChangeEvent.getProxyMetaDefinition());
        switch (proxyMetaDefinitionChangeEvent.getOperateEventEnum()){
            case ADD:
                aopPluginFactory.installPlugin(proxyMetaInfo);
                break;
            case DEL:
                aopPluginFactory.uninstallPlugin(proxyMetaInfo.getId());
                break;
        }

    }
}

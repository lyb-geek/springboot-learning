package com.github.lybgeek.aop.core.locator.support;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.aop.core.locator.ProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CompositeProxyMetaDefinitionLocator implements ProxyMetaDefinitionLocator {

    private final List<ProxyMetaDefinitionLocator> delegates;

    @Override
    public List<ProxyMetaDefinition> getProxyMetaDefinitions() {
        List<ProxyMetaDefinition> compositeProxyMetaDefinitions = new ArrayList<>();
        for (ProxyMetaDefinitionLocator delegate : delegates) {
            List<ProxyMetaDefinition> proxyMetaDefinitions = delegate.getProxyMetaDefinitions();
            if(CollectionUtil.isNotEmpty(proxyMetaDefinitions)){
                compositeProxyMetaDefinitions.addAll(proxyMetaDefinitions);
            }

        }
        return compositeProxyMetaDefinitions;
    }
}

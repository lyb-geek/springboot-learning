package com.github.lybgeek.aop.core.locator.support;


import com.github.lybgeek.aop.core.config.ProxyProperties;
import com.github.lybgeek.aop.core.locator.ProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PropertiesProxyMetaDefinitionLocator implements ProxyMetaDefinitionLocator {

    private final ProxyProperties properties;

    @Override
    public List<ProxyMetaDefinition> getProxyMetaDefinitions() {
        return properties.getMetaDefinitions();
    }
}

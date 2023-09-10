package com.github.lybgeek.aop.core.locator;


import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;

import java.util.List;

@FunctionalInterface
public interface ProxyMetaDefinitionLocator {

    List<ProxyMetaDefinition> getProxyMetaDefinitions();


}

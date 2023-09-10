package com.github.lybgeek.aop.core.locator.repository;


import com.github.lybgeek.aop.core.locator.ProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;

public interface ProxyMetaDefinitionRepository extends ProxyMetaDefinitionLocator,ProxyMetaDefinitionWriter {

     ProxyMetaDefinition getProxyMetaDefinition(String proxyMetaDefinitionId);
}

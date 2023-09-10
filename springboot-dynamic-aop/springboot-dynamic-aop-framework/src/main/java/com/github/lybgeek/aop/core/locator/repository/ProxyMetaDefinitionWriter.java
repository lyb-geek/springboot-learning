package com.github.lybgeek.aop.core.locator.repository;


import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;

public interface ProxyMetaDefinitionWriter {

    void save(ProxyMetaDefinition definition);

    void delete(String proxyMetaDefinitionId);
}

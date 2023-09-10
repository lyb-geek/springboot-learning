package com.github.lybgeek.aop.core.locator.repository.impl;


import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import org.springframework.util.StringUtils;

import java.util.*;

import static java.util.Collections.synchronizedMap;

public class InMemoryProxyMetaDefinitionRepository extends BaseProxyMetaDefinitionRepository {

    private final Map<String, ProxyMetaDefinition> proxyMetaDefinitionMap = synchronizedMap(
            new LinkedHashMap());

    @Override
    public List<ProxyMetaDefinition> getProxyMetaDefinitions() {
        return new ArrayList<>(proxyMetaDefinitionMap.values());
    }

    @Override
    public ProxyMetaDefinition getProxyMetaDefinition(String proxyMetaDefinitionId) {
        return proxyMetaDefinitionMap.get(proxyMetaDefinitionId);
    }

    @Override
    public boolean saveProxyMetaDefinition(ProxyMetaDefinition definition) {
        if(StringUtils.isEmpty(definition.getId())){
           throw new IllegalArgumentException("Id can not be empty");
        }
        proxyMetaDefinitionMap.put(definition.getId(), definition);
        return true;
    }

    @Override
    public boolean deleteProxyMetaDefinition(String proxyMetaDefinitionId) {
        if(proxyMetaDefinitionMap.containsKey(proxyMetaDefinitionId)){
            proxyMetaDefinitionMap.remove(proxyMetaDefinitionId);
        }else{
            throw new NoSuchElementException("ProxyMetaDefinition not found: " + proxyMetaDefinitionId);
        }
        return true;
    }
}

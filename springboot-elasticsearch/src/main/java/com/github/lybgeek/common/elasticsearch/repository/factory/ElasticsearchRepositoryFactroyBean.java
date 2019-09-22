package com.github.lybgeek.common.elasticsearch.repository.factory;

import com.github.lybgeek.common.elasticsearch.repository.proxy.ElasticsearchRepositoryProxy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticsearchRepositoryFactroyBean<T> implements FactoryBean<T> {

    private Class elasticsearchRepositoryClz;


    @Override
    public T getObject() throws Exception {


        return (T) new ElasticsearchRepositoryProxy().getInstance(elasticsearchRepositoryClz);
    }

    @Override
    public Class<?> getObjectType() {
        return elasticsearchRepositoryClz;
    }
}

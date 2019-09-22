package com.github.lybgeek.common.elasticsearch.repository;

import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import org.springframework.stereotype.Component;

@Component
public class CustomSimpleElasticsearchRepository<T, ID> extends AbstractCustomElasticsearchRepository<T, ID> {

  public CustomSimpleElasticsearchRepository(
      ElasticsearchHelper elasticsearchHelper) {

    super(elasticsearchHelper);
  }
}

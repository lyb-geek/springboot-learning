package com.github.lybgeek.common.elasticsearch.repository;

import com.github.lybgeek.common.model.PageResult;
import java.util.Collection;
import java.util.List;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public interface CustomElasticsearchRepository<T, ID> {
  T index(T entity);

  List<T> search(SearchSourceBuilder builder);

  PageResult<T> page(SearchSourceBuilder builder);

  boolean deleteIndex(String indexName);

  boolean isIndexExist(String indexName);

  boolean deleteByQuery(QueryBuilder builder);

  boolean deleteBatch(Collection<ID> idList);

  boolean deleteById(ID id);

  boolean insertBatch(List<T> entitys);

  boolean save(T entity);

  T getEntityById(ID id);

}

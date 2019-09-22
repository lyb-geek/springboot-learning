package com.github.lybgeek.common.elasticsearch.repository;

import com.github.lybgeek.common.elasticsearch.annotation.EsDocument;
import com.github.lybgeek.common.elasticsearch.annotation.EsId;
import com.github.lybgeek.common.elasticsearch.model.EsEntity;
import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import com.github.lybgeek.common.model.PageResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.Assert;

@Slf4j
public abstract class AbstractCustomElasticsearchRepository <T, ID> implements CustomElasticsearchRepository<T, ID>{

  private Map<String,String> indexNameMap = new ConcurrentHashMap<>();

  private Map<String,String> idMap = new ConcurrentHashMap<>();

  protected ElasticsearchHelper elasticsearchHelper;

  protected Class<T> entityClass;


  public AbstractCustomElasticsearchRepository(ElasticsearchHelper elasticsearchHelper) {

    this.elasticsearchHelper = elasticsearchHelper;
  }

  @Override
  public T index(T entity) {

    elasticsearchHelper.createIndex(entity.getClass());
    return entity;
  }

  @Override
  public List<T> search(SearchSourceBuilder builder) {
    String indexName = this.getIndexName();
    return elasticsearchHelper.search(indexName,builder,entityClass);
  }

  @Override
  public PageResult<T> page(SearchSourceBuilder builder) {
    String indexName = this.getIndexName();
    return elasticsearchHelper.pageSearch(indexName,builder,entityClass);
  }

  @Override
  public boolean deleteIndex(String indexName) {

    return elasticsearchHelper.deleteIndex(indexName);
  }

  @Override
  public boolean isIndexExist(String indexName) {

    return elasticsearchHelper.isIndexExist(indexName);
  }

  @Override
  public boolean deleteByQuery(QueryBuilder builder) {
    String indexName = this.getIndexName();
    return elasticsearchHelper.deleteByQuery(indexName,builder);
  }

  @Override
  public boolean deleteBatch(Collection<ID> idList) {
    String indexName = this.getIndexName();
    return elasticsearchHelper.deleteBatch(indexName,idList);
  }

  @Override
  public boolean insertBatch(List<T> entitys) {
    String indexName = this.getIndexName();
    List<EsEntity> esEntities = this.listEsEntitys(entitys);
    return elasticsearchHelper.insertBatch(indexName,esEntities);
  }

  @Override
  public boolean save(T entity) {
    String indexName = this.getIndexName();
    EsEntity esEntity = this.convertDoToEsEntity(entity);
    return elasticsearchHelper.saveOrUpdate(indexName,esEntity);
  }

  @Override
  public boolean deleteById(ID id) {
    Collection<ID> idList = new ArrayList<>();
    ((ArrayList<ID>) idList).add(id);
    return deleteBatch(idList);
  }

  @Override
  public T getEntityById(ID id) {
    String idName = this.getEsIdFieldName();
    QueryBuilder builder = QueryBuilders.termQuery(idName, id);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(builder);
    List<T> entitys = this.search(searchSourceBuilder);

    return CollectionUtils.isNotEmpty(entitys) ? entitys.get(0) : null;
  }

  private final String getIndexName(){
    String entityName = entityClass.getSimpleName();
    if(StringUtils.isNotBlank(indexNameMap.get(entityName))){
      return indexNameMap.get(entityName);
    }
    EsDocument esDocument =  entityClass.getAnnotation(EsDocument.class);
    Assert.notNull(esDocument, entityClass.getName()+ " must has EsDocument annotation!");
    String indexName = esDocument.indexName();
    indexNameMap.put(entityName,indexName);
    return indexName;
  }


  private final EsEntity <T> convertDoToEsEntity(T entity){
    EsEntity esEntity = new EsEntity();
    Object id = elasticsearchHelper.getEsId(entity);
    if(ObjectUtils.isNotEmpty(id)){
      esEntity.setId(id.toString());
    }
    esEntity.setData(entity);
    return esEntity;
  }

  private final List<EsEntity> listEsEntitys(List<T> entitys){
    List<EsEntity> esEntities = new ArrayList<>();
    if(CollectionUtils.isNotEmpty(entitys)){
      for (T entity : entitys) {
        EsEntity <T> esEntity = convertDoToEsEntity(entity);
        esEntities.add(esEntity);
      }
    }
    return esEntities;
  }

  private final String getEsIdFieldName(){
    String entityName = entityClass.getSimpleName();
    if(StringUtils.isNotBlank(idMap.get(entityName))){
      return idMap.get(entityName);
    }
    List<Field> fields = FieldUtils.getFieldsListWithAnnotation(entityClass, EsId.class);
    Assert.isTrue(CollectionUtils.isNotEmpty(fields),entityClass.getName()+ " must has EsId annotation on field!");

    String esIdFieldName = fields.get(0).getName();
    idMap.put(entityName,esIdFieldName);
    return esIdFieldName;
  }

  public Class<T> getEntityClass() {

    return entityClass;
  }

  public void setEntityClass(Class<T> entityClass) {

    this.entityClass = entityClass;
  }
}

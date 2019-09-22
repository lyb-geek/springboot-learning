package com.github.lybgeek.elasticsearch.aspect;

import com.github.lybgeek.common.elasticsearch.annotation.EsDocument;
import com.github.lybgeek.common.elasticsearch.annotation.EsField;
import com.github.lybgeek.common.elasticsearch.annotation.EsId;
import com.github.lybgeek.common.elasticsearch.model.EsEntity;
import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.elasticsearch.annotation.EsOperate;
import com.github.lybgeek.elasticsearch.enu.OperateType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
@Aspect
@Slf4j
public class ElasticsearchAspect {

  @Autowired
  private ElasticsearchHelper elasticsearchHelper;

  @AfterReturning(value = "@annotation(operate)",returning = "retVal")
  public void afterReturning(JoinPoint jp,Object retVal, EsOperate operate){

    OperateType type = operate.type();

    switch (type){
      case ADD:
        saveOrUpdate(jp,retVal,operate);
        break;
      case UPDATE:
        saveOrUpdate(jp,retVal,operate);
        break;
      case DELETE:

    }

  }

  @After(value="@annotation(operate)")
  public void after(JoinPoint jp,EsOperate operate){
    OperateType type = operate.type();
    switch (type){
      case DELETE:
        delete(jp,operate);
        break;
    }
  }


  @Around(value="@annotation(operate)")
  public Object around(ProceedingJoinPoint pjp,EsOperate operate){
    OperateType type = operate.type();
    Object[] args = pjp.getArgs();
    Object result = null;
    String methodName = pjp.getSignature().getName();
    if(ArrayUtils.isNotEmpty(args) && args.length == 1){
      Object param = args[0];
      switch (type){
        case QUERY:
          if(param instanceof PageQuery){
            PageQuery pageQuery = (PageQuery) param;
            result = this.pageQuery(pageQuery,operate.indexName());
          }else{
            result = this.query(param,operate.indexName());
          }
          break;
      }

    }

    if(ObjectUtils.isEmpty(result)){
      try {
        log.info("{} load data from db",methodName);
        return pjp.proceed();
      } catch (Throwable throwable) {
        log.error(throwable.getMessage(),throwable);
      }
    }

    if(OperateType.QUERY == type){
      log.info("{} load data from es",methodName);
    }


    return result;
  }




  private Object query(Object queryParams,String indexName){

    SearchSourceBuilder searchSourceBuilder = getSearchSouceBuilder(queryParams);
    if(ObjectUtils.isNotEmpty(searchSourceBuilder)){
      return elasticsearchHelper.search(indexName,searchSourceBuilder,queryParams.getClass());
    }
    return null;
  }

  private Object pageQuery(PageQuery pageQuery,String indexName){
    int pageNo = ObjectUtils.isNotEmpty(pageQuery.getPageNo()) && pageQuery.getPageNo() > 0 ? pageQuery.getPageNo() : 1;
    int pageSize = ObjectUtils.isNotEmpty(pageQuery.getPageSize()) ? pageQuery.getPageSize() : 10;
    int from = (pageNo - 1) * pageSize;
    Object queryParams = pageQuery.getQueryParams();
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    if(ObjectUtils.isNotEmpty(queryParams)){
      searchSourceBuilder = getSearchSouceBuilder(queryParams);
    }
    if(ObjectUtils.isNotEmpty(searchSourceBuilder)){
      searchSourceBuilder.from(from).size(pageSize);
      PageResult pageResult = elasticsearchHelper.pageSearch(indexName,searchSourceBuilder,queryParams.getClass());
      if(CollectionUtils.isEmpty(pageResult.getList())){
        return null;
      }
    }
    return null;
  }

  private SearchSourceBuilder getSearchSouceBuilder(Object queryParams) {

    Map<String,Object> queryMap = this.getQueryMap(queryParams);
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    QueryBuilder queryBuilder = new BoolQueryBuilder();
    queryMap.forEach((key,value)->{
      ((BoolQueryBuilder) queryBuilder).must(QueryBuilders.matchQuery(key,value));
    });
    searchSourceBuilder.query(queryBuilder);
    return searchSourceBuilder;
  }

  private Map<String,Object> getQueryMap(Object queryParams){
     Map<String,Object> queryMap = new HashMap<>();
     boolean hasEsDocumentAnnotation = queryParams.getClass().isAnnotationPresent(EsDocument.class);
     if(hasEsDocumentAnnotation){
       ReflectionUtils.doWithFields(queryParams.getClass(),field -> {
         ReflectionUtils.makeAccessible(field);
          EsField esFieldAnnotation = field.getAnnotation(EsField.class);
          if(ObjectUtils.isNotEmpty(esFieldAnnotation)){
            String key = StringUtils.isNotBlank(esFieldAnnotation.value()) ? esFieldAnnotation.value() : field.getName();
            Object value = field.get(queryParams);
            if(ObjectUtils.isNotEmpty(value)){
              queryMap.put(key,value);
            }
          }
       });

     }

       return queryMap;
  }


  private void delete(JoinPoint jp, EsOperate operate){
     Object[] args = jp.getArgs();
     String methodName  = jp.getSignature().getName();
     boolean isDeleteOk = false;
     if(ObjectUtils.isNotEmpty(args) && args.length == 1){
       List<Object> ids = new ArrayList<>();
       ids.add(args[0]);
       isDeleteOk = elasticsearchHelper.deleteBatch(operate.indexName(),ids);
     }

    log.info("{} saveOrUpdateToEs success {}",methodName,isDeleteOk);
  }


  private void saveOrUpdate(JoinPoint jp,Object retVal, EsOperate operate){
    String methodName  = jp.getSignature().getName();

    Object id = elasticsearchHelper.getEsId(retVal);
    EsEntity esEntity = new EsEntity();
    esEntity.setData(retVal);
    if(ObjectUtils.isNotEmpty(id)){
      esEntity.setId(id.toString());
    }
    boolean isOK = elasticsearchHelper.saveOrUpdate(operate.indexName(),esEntity);

    log.info("{} saveOrUpdateToEs success {}",methodName,isOK);
  }





}

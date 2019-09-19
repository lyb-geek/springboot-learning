package com.github.lybgeek.elasticsearch.service.impl;

import com.github.lybgeek.common.elasticsearch.model.EsEntity;
import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.elasticsearch.constant.ElasticsearchConstant;
import com.github.lybgeek.elasticsearch.service.CustomShortUrlEsService;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomShortUrlEsServiceImpl implements CustomShortUrlEsService {

  @Autowired
  private ElasticsearchHelper elasticsearchHelper;


  @Override
  public String save(ShortUrlDTO shortUrlDTO) {
    EsEntity esEntity = EsEntity.builder().id(shortUrlDTO.getId().toString()).data(shortUrlDTO).build();
    boolean isOk = elasticsearchHelper.saveOrUpdate(ElasticsearchConstant.SHORT_URL_INDEX,esEntity);

    if(isOk){
      return esEntity.getId();
    }

    return null;
  }

  @Override
  public PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery) {

    int pageNo = ObjectUtils.isNotEmpty(pageQuery.getPageNo()) && pageQuery.getPageNo() > 0 ? pageQuery.getPageNo() : 1;
    int pageSize = ObjectUtils.isNotEmpty(pageQuery.getPageSize()) ? pageQuery.getPageSize() : 10;
    ShortUrlDTO queryParams = pageQuery.getQueryParams();

    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    int from = (pageNo - 1) * pageSize;
    searchSourceBuilder.from(from).size(pageSize).sort("id", SortOrder.DESC);
    if(ObjectUtils.isNotEmpty(queryParams)) {
      BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

      if (queryParams != null) {
        if (StringUtils.isNotBlank(queryParams.getLongUrl())) {
          QueryBuilder builder = QueryBuilders.matchQuery("longUrl", queryParams.getLongUrl());
          boolBuilder.must(builder);

        }

        if (StringUtils.isNotBlank(queryParams.getRemark())) {
          QueryBuilder builder = QueryBuilders.matchQuery("remark", queryParams.getRemark());
          boolBuilder.must(builder);
        }

        if (StringUtils.isNotBlank(queryParams.getUrlName())) {
          QueryBuilder builder = QueryBuilders.matchQuery("urlName", queryParams.getUrlName());
          boolBuilder.must(builder);
        }

        searchSourceBuilder.query(boolBuilder);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightUrlName =
            new HighlightBuilder.Field("urlName").preTags("<strong>").postTags("</strong>");
        highlightUrlName.highlighterType("unified");
        highlightBuilder.field(highlightUrlName);
        searchSourceBuilder.highlighter(highlightBuilder);
      }

    }

    PageResult<ShortUrlDTO> pageResult = elasticsearchHelper.pageSearch(ElasticsearchConstant.SHORT_URL_INDEX,searchSourceBuilder,ShortUrlDTO.class);

    return pageResult;
  }
}

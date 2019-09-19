package com.github.lybgeek.elasticsearch.service.impl;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.util.BeanMapperUtil;
import com.github.lybgeek.common.util.PageUtil;
import com.github.lybgeek.elasticsearch.model.ShortUrlVO;
import com.github.lybgeek.elasticsearch.repository.ShortUrlEsRepository;
import com.github.lybgeek.elasticsearch.service.ShortUrlEsService;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service("shortUrlEsService")
public class ShortUrlEsServiceImpl implements ShortUrlEsService {

  //@Autowired
  private ShortUrlEsRepository shortUrlEsRepository;

  @Override
  public ShortUrlVO save(ShortUrlDTO shortUrlDTO) {
    ShortUrlVO shortUrlVO = BeanMapperUtil.map(shortUrlDTO,ShortUrlVO.class);

    return shortUrlEsRepository.save(shortUrlVO);
  }

  @Override
  public PageResult<ShortUrlVO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery) {

    int pageNo = ObjectUtils.isNotEmpty(pageQuery.getPageNo())? pageQuery.getPageNo() - 1 : 0;
    int pageSize = ObjectUtils.isNotEmpty(pageQuery.getPageSize()) ? pageQuery.getPageSize() : 10;
    Sort sort = new Sort(Direction.DESC,"id");
    Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
    ShortUrlDTO queryParams = pageQuery.getQueryParams();
    BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();

    if(queryParams != null){
      if(StringUtils.isNotBlank(queryParams.getLongUrl())){
        QueryBuilder builder = QueryBuilders.matchQuery("longUrl",queryParams.getLongUrl());
        boolBuilder.must(builder);

      }

      if(StringUtils.isNotBlank(queryParams.getRemark())){
        QueryBuilder builder = QueryBuilders.matchQuery("remark",queryParams.getRemark());
        boolBuilder.must(builder);
      }

      if(StringUtils.isNotBlank(queryParams.getUrlName())){
        QueryBuilder builder = QueryBuilders.matchQuery("urlName",queryParams.getUrlName());
        boolBuilder.must(builder);

      }

    }
    Page<ShortUrlVO> page = shortUrlEsRepository.search(boolBuilder,pageable);

    return PageUtil.INSTANCE.getPage(page,page.getContent());
  }
}

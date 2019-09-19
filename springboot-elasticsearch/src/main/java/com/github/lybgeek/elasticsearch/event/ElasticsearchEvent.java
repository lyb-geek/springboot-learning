package com.github.lybgeek.elasticsearch.event;

import com.github.lybgeek.common.elasticsearch.model.EsEntity;
import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import com.github.lybgeek.elasticsearch.constant.ElasticsearchConstant;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.model.ShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ElasticsearchEvent {

  @Autowired
  private ElasticsearchHelper elasticsearchHelper;

  @Async
  @EventListener
  public void syncShortUrlToEs(ShortUrl shortUrl){

    ShortUrlDTO shortUrlDO = new ShortUrlDTO();
    BeanUtils.copyProperties(shortUrl,shortUrlDO);
    log.info("syncShortUrlToEs -> {}",shortUrlDO);
    EsEntity esEntity = EsEntity.builder().id(shortUrlDO.getId().toString()).data(shortUrlDO).build();
    elasticsearchHelper.saveOrUpdate(ElasticsearchConstant.SHORT_URL_INDEX,esEntity);
  }

}

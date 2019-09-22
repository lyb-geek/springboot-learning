package com.github.lybgeek.elasticsearch.repository;

import com.github.lybgeek.common.elasticsearch.repository.CustomElasticsearchRepository;
import com.github.lybgeek.common.elasticsearch.repository.anntation.ElasticsearchRepository;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;

@ElasticsearchRepository
public interface CustomShortUrlEsRepository extends CustomElasticsearchRepository<ShortUrlDTO,Long>{
}

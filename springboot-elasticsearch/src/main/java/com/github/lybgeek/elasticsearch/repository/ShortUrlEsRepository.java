package com.github.lybgeek.elasticsearch.repository;

import com.github.lybgeek.elasticsearch.model.ShortUrlVO;
import com.github.lybgeek.shorturl.model.ShortUrl;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ShortUrlEsRepository extends ElasticsearchRepository<ShortUrlVO,String>{
}

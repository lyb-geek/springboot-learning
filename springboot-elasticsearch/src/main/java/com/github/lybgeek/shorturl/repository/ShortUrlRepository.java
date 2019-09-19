package com.github.lybgeek.shorturl.repository;

import com.github.lybgeek.shorturl.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> ,
    JpaSpecificationExecutor<ShortUrl> {
}

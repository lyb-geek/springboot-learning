package com.github.lybgeek.shorturl.repository;

import com.github.lybgeek.shorturl.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl,Long> {
}

package com.github.lybgeek.shorturl.dao;

import com.github.lybgeek.shorturl.model.ShortUrl;
import com.github.lybgeek.shorturl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShortUrlDao {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public ShortUrl save(ShortUrl shortUrl){
        return shortUrlRepository.save(shortUrl);
    }

    public ShortUrl getShortUrlById(Long id){
        return shortUrlRepository.getOne(id);
    }
}

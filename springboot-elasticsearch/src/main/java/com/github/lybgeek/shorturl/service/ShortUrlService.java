package com.github.lybgeek.shorturl.service;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.model.ShortUrl;
import java.util.List;

public interface ShortUrlService {

    String saveAndReturnShortUrl(ShortUrlDTO shortUrlDTO);


    PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery);

    String getLongUrlByShortUrl(String shortUrl);

    String getLongUrlByRadixStr(String radixStr);

    ShortUrlDTO saveShortUrl(ShortUrlDTO shortUrlDTO);

    boolean deleteShortUrlById(Long id);

    List<ShortUrlDTO> listShortUrls(ShortUrlDTO shortUrlDTO);

}

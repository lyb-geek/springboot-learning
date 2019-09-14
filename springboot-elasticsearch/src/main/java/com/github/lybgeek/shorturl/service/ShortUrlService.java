package com.github.lybgeek.shorturl.service;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;

public interface ShortUrlService {

    String saveAndReturnShortUrl(ShortUrlDTO shortUrlDTO);


    PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery);

    String getLongUrlByShortUrl(String shortUrl);

    String getLongUrlByRadixStr(String radixStr);

}

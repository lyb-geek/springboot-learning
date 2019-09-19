package com.github.lybgeek.elasticsearch.service;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;

public interface CustomShortUrlEsService {

  String save(ShortUrlDTO shortUrlDTO);

  PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery);

}

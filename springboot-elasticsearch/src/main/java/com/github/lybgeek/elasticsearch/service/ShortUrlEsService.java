package com.github.lybgeek.elasticsearch.service;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.elasticsearch.model.ShortUrlVO;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;

public interface ShortUrlEsService {

  ShortUrlVO save(ShortUrlDTO shortUrlDTO);

  PageResult<ShortUrlVO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery);

}

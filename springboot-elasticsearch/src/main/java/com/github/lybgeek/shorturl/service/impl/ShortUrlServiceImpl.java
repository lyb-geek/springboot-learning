package com.github.lybgeek.shorturl.service.impl;

import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.util.BeanMapperUtil;
import com.github.lybgeek.shorturl.dao.ShortUrlDao;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.model.ShortUrl;
import com.github.lybgeek.shorturl.service.ShortUrlService;
import com.github.lybgeek.shorturl.util.ShortUrlUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    private ShortUrlDao shortUrlDao;

    @Override
    public String saveAndReturnShortUrl(ShortUrlDTO shortUrlDTO) {
        ShortUrl shortUrl = BeanMapperUtil.map(shortUrlDTO, ShortUrl.class);
        ShortUrl dbShortUrl = shortUrlDao.save(shortUrl);

        return ShortUrlUtil.getShortUrl(dbShortUrl.getId());
    }


    @Override
    public PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery) {
        return null;
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        String radixStr = StringUtils.replace(shortUrl, ShortUrlUtil.SHORT_URL_PREFIX, "");
        return getLongUrlByRadixStr(radixStr);

    }

    @Override
    public String getLongUrlByRadixStr(String radixStr){
        Long id = ShortUrlUtil.decode(radixStr);
        if (ObjectUtils.isNotEmpty(id)) {
            ShortUrl url = shortUrlDao.getShortUrlById(id);
            if (ObjectUtils.isNotEmpty(url)) {
                return url.getLongUrl();
            }
        }

        throw new BizException("找不到匹配的长链接", 404);
    }


}

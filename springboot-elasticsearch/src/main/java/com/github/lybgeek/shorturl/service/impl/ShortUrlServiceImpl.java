package com.github.lybgeek.shorturl.service.impl;

import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.util.BeanMapperUtil;
import com.github.lybgeek.common.util.PageUtil;
import com.github.lybgeek.elasticsearch.annotation.EsOperate;
import com.github.lybgeek.elasticsearch.constant.ElasticsearchConstant;
import com.github.lybgeek.elasticsearch.enu.OperateType;
import com.github.lybgeek.elasticsearch.model.ShortUrlVO;
import com.github.lybgeek.elasticsearch.service.ShortUrlEsService;
import com.github.lybgeek.shorturl.dao.ShortUrlDao;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.model.ShortUrl;
import com.github.lybgeek.shorturl.service.ShortUrlService;
import com.github.lybgeek.shorturl.util.ShortUrlUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class ShortUrlServiceImpl implements ShortUrlService, ApplicationEventPublisherAware {

    @Autowired
    private ShortUrlDao shortUrlDao;


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public String saveAndReturnShortUrl(ShortUrlDTO shortUrlDTO) {
        ShortUrl shortUrl = BeanMapperUtil.map(shortUrlDTO, ShortUrl.class);
        ShortUrl dbShortUrl = shortUrlDao.save(shortUrl);
        applicationEventPublisher.publishEvent(dbShortUrl);
        return ShortUrlUtil.getShortUrl(dbShortUrl.getId());
    }


    @Override
    @EsOperate(type = OperateType.QUERY,indexName = ElasticsearchConstant.SHORT_URL_INDEX)
    public PageResult<ShortUrlDTO> pageShortUrl(PageQuery<ShortUrlDTO> pageQuery) {
        PageQuery<ShortUrl> query = getShortUrlPageQuery(pageQuery);
        PageResult<ShortUrl> page = shortUrlDao.pageShortUrls(query);
        List<ShortUrlDTO> list = BeanMapperUtil.mapList(page.getList(),ShortUrlDTO.class);
        PageResult<ShortUrlDTO> pageResult = new PageResult<>();
        BeanUtils.copyProperties(page,pageResult);
        pageResult.setList(list);
        return pageResult;
    }

    private PageQuery<ShortUrl> getShortUrlPageQuery(PageQuery<ShortUrlDTO> pageQuery) {

        PageQuery<ShortUrl> query = new PageQuery<>();
        BeanUtils.copyProperties(pageQuery,query);
        ShortUrl shortUrl = BeanMapperUtil.map(pageQuery.getQueryParams(),ShortUrl.class);
        query.setQueryParams(shortUrl);
        return query;
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

    @Override
    @EsOperate(type = OperateType.ADD,indexName = ElasticsearchConstant.SHORT_URL_INDEX)
    public ShortUrlDTO saveShortUrl(ShortUrlDTO shortUrlDTO) {
        ShortUrl shortUrl = BeanMapperUtil.map(shortUrlDTO, ShortUrl.class);
        ShortUrl dbShortUrl = shortUrlDao.save(shortUrl);

        return BeanMapperUtil.map(dbShortUrl,ShortUrlDTO.class);
    }

    @Override
    @EsOperate(type = OperateType.DELETE,indexName = ElasticsearchConstant.SHORT_URL_INDEX)
    public boolean deleteShortUrlById(Long id) {

        return shortUrlDao.deleteShortUrlById(id);
    }

    @Override
    @EsOperate(type = OperateType.QUERY,indexName = ElasticsearchConstant.SHORT_URL_INDEX)
    public List<ShortUrlDTO> listShortUrls(ShortUrlDTO shortUrlDTO) {
        ShortUrl shortUrl = BeanMapperUtil.map(shortUrlDTO, ShortUrl.class);
        List<ShortUrl> shortUrls = shortUrlDao.listShortUrls(shortUrl);
        return BeanMapperUtil.mapList(shortUrls,ShortUrlDTO.class);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

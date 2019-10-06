package com.github.lybgeek.httpclient.strategy.impl;

import com.github.lybgeek.httpclient.annotation.HttpClient;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import com.github.lybgeek.httpclient.strategy.HttpClientStrategy;
import com.github.lybgeek.httpclient.util.WebClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@HttpClient(type = HttpclientTypeEnum.WEB_CLIENT)
@Slf4j
public class WebClientStrategy implements HttpClientStrategy {

    @Autowired
    private WebClientUtil webClientUtil;
    @Override
    public String postForm(String url, Map<String, String> params, Map<String, String> headers) {
        if(MapUtils.isEmpty(params)){
            params = new HashMap<>();
        }
        log.info("use {},url:{},params:{},headers:{}...",getClass().getSimpleName(),url,params,headers);
        return webClientUtil.postForm(params,url,headers,String.class);
    }
}

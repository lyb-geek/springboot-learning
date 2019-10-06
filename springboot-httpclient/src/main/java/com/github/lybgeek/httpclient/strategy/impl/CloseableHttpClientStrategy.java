package com.github.lybgeek.httpclient.strategy.impl;

import com.github.lybgeek.httpclient.annotation.HttpClient;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import com.github.lybgeek.httpclient.strategy.HttpClientStrategy;
import com.github.lybgeek.httpclient.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@HttpClient(type = HttpclientTypeEnum.HTTP_CLIENT)
@Slf4j
public class CloseableHttpClientStrategy implements HttpClientStrategy {

    @Autowired
    private HttpClientUtil httpClientUtil;
    @Override
    public String postForm(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("use {},url:{},params:{},headers:{}...",getClass().getSimpleName(),url,params,headers);
        return httpClientUtil.doPost(url,params,headers);
    }
}

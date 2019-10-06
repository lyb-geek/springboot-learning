package com.github.lybgeek.httpclient.strategy.impl;

import com.github.lybgeek.httpclient.annotation.HttpClient;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;
import com.github.lybgeek.httpclient.strategy.HttpClientStrategy;
import com.github.lybgeek.httpclient.util.RestTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@HttpClient(type = HttpclientTypeEnum.REST_TEMPLATE)
@Slf4j
public class RestTemplateStrategy implements HttpClientStrategy {

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Override
    public String postForm(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("use {},url:{},params:{},headers:{}...",getClass().getSimpleName(),url,params,headers);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，不要替换为Map与HashMap，否则参数无法传递，因为表单提交只能用MultiValueMap
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.setAll(params);
        httpHeaders.setAll(headers);
        ResponseEntity<String> responseEntity = restTemplateUtil.post(url,httpHeaders,paramsMap,String.class,new HashMap<>());
        if(ObjectUtils.isNotEmpty(responseEntity)){
            return responseEntity.getBody();
        }
        return StringUtils.EMPTY;
    }
}

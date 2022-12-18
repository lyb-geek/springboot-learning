package com.github.lybgeek.http.support;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.http.HttpTemplate;
import com.github.lybgeek.http.common.HttpMethod;
import com.github.lybgeek.http.common.constant.HttpCommon;
import com.github.lybgeek.http.common.exception.HttpException;

import java.util.Map;

public class DefaultHttpTemplate implements HttpTemplate {

    @Override
    public boolean supportsSourceType(String sourceType) {
        return HttpCommon.DEFAULT_HTTP_TYPE.equals(sourceType);
    }

    @Override
    public Object execute(String url, HttpMethod httpMethod, Map<String, String> headerMap, Map<String, Object> paramMap) {
        Object result = null;
        switch (httpMethod){
            case POST:
               result = HttpRequest.post(url)
                        .addHeaders(headerMap)
                        .body(JSONUtil.toJsonStr(paramMap))
                        .execute();
                break;
            case GET:
                result = HttpRequest.get(url)
                        .addHeaders(headerMap)
                        .form(paramMap)
                        .execute();
                break;
            default:
                throw new HttpException("NO SUPPORT HTTP METHOD WITH : [" + httpMethod.name() + "]");
        }
        return result;
    }


}

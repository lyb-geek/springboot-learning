package com.github.lybgeek.kafka.consumer.service.impl;


import cn.hutool.json.JSONUtil;
import com.dtflys.forest.Forest;
import com.dtflys.forest.http.ForestResponse;
import com.github.lybgeek.kafka.consumer.model.Request;
import com.github.lybgeek.kafka.consumer.service.HttpTemplate;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DefaultHttpTemplate implements HttpTemplate {


    @Override
    public Object get(Request request) {
        Map paramMap = getParamMap(request.getParamRequest().getRequestParamJson());
        ForestResponse response = Forest.config().get(request.getUrl())
                .contentFormUrlEncoded()
                .addQuery(paramMap)
                .executeAsResponse();
        if(response.isSuccess()){
            return response.getResult();
        }
        return null;
    }

    @Override
    public Object post(Request request) {
        Map paramMap = getParamMap(request.getParamRequest().getRequestParamJson());
        ForestResponse response = Forest.config().post(request.getUrl()).addBody(paramMap).executeAsResponse();
        if(response.isSuccess()){
            return response.getResult();
        }
        return null;
    }

    @Override
    public Object postJson(Request request) {
        ForestResponse response = Forest.config().post(request.getUrl()).contentTypeJson().addBody(request.getParamRequest().getRequestParamJson()).executeAsResponse();
        if(response.isSuccess()){
            return response.getResult();
        }
        return null;
    }

    private Map getParamMap(String requestParamJson){
        if(JSONUtil.isJson(requestParamJson)){
            return JSONUtil.toBean(requestParamJson,Map.class);
        }
        return null;
    }
}

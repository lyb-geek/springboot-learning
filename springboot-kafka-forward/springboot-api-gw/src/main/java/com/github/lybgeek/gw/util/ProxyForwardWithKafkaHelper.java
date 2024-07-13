package com.github.lybgeek.gw.util;


import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.kafka.enums.HttpMethodEnum;
import com.github.lybgeek.kafka.model.ParamRequest;
import com.github.lybgeek.kafka.producer.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.github.lybgeek.kafka.constant.KafkaConstant.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class ProxyForwardWithKafkaHelper {

    private final KafkaService kafkaService;

    private final ThreadLocal<String> topicThreadLocal = new ThreadLocal<>();

    public Mono<ResponseEntity<byte[]>> forward(ProxyExchange<byte[]> proxy, Object params, HttpMethodEnum httpMethodEnum){
        try {
            String path = proxy.path().replace("/kafka", "").trim();
            ParamRequest paramRequest = buildParamRequest(path,params,httpMethodEnum);
            String topicCode = StringUtils.hasText(topicThreadLocal.get()) ? topicThreadLocal.get() : DEFAULT_TOPIC;
            String topic = TOPIC.replace(DEFAULT_TOPIC_PATTERN,topicCode);
            log.info(">>>>>>>>>>>>>> topic:{},httpMethod:{}, path:{},params:{}",topic,httpMethodEnum,path,params);
            Object result = kafkaService.sendAndReceive(topic,paramRequest);
            if(result != null){
                return Mono.just(ResponseEntity.ok(result.toString().getBytes()));
            }
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>> httpMethod:{},forward --> e:{}",httpMethodEnum.toString(),e.getMessage());
        } finally {
            topicThreadLocal.remove();
        }
        return Mono.just(ResponseEntity.ok(new byte[0]));
    }


    private ParamRequest buildParamRequest(String url, Object params, HttpMethodEnum httpMethodEnum){
        switch (httpMethodEnum){
            case POST:
                return ParamRequest.builder().requestParamJson(rebuildParams(params)).requestUrl(url).httpMethod(HttpMethodEnum.POST).build();
            case POST_JSON:
                return ParamRequest.builder().requestParamJson(rebuildParams(params)).requestUrl(url).httpMethod(HttpMethodEnum.POST_JSON).build();
            case GET:
                return ParamRequest.builder().requestParamJson(rebuildParams(params)).requestUrl(url).httpMethod(HttpMethodEnum.GET).build();
            default:
                return null;
        }
    }

    private String rebuildParams(Object params) {
        if(params instanceof Map){
            Map<String,Object> paramMap = rebuildParamsMap((Map<String,Object>)params);
            return JSONUtil.toJsonStr(paramMap);
        }else if(params != null && JSONUtil.isJson(params.toString())){
            Map<String,Object> paramMap = JSONUtil.toBean(params.toString(), Map.class);
            return JSONUtil.toJsonStr(rebuildParamsMap(paramMap));
        }
        return null;

    }

    private Map<String,Object> rebuildParamsMap(Map<String,Object> params) {
        if(MapUtil.isNotEmpty(params) && params.containsKey(TOPIC_PARAM_KEY)){
            topicThreadLocal.set(params.get(TOPIC_PARAM_KEY).toString());
            params.remove(TOPIC_PARAM_KEY);
            return params;
        }
        return params;
    }




}

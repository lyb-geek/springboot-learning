package com.github.lybgeek.gw.controller;


import com.github.lybgeek.gw.util.DataBufferUtil;
import com.github.lybgeek.gw.util.ProxyForwardWithKafkaHelper;
import com.github.lybgeek.kafka.enums.HttpMethodEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kafka")
@Slf4j
@RequiredArgsConstructor
public class ProxyForwardWithKafkaController {

    private final ProxyForwardWithKafkaHelper proxyForwardWithKafkaHelper;



    @GetMapping(value = "/**")
    public Mono<ResponseEntity<byte[]>> proxyGet(ProxyExchange<byte[]> proxy, @RequestParam(required = false) Map<String,Object> params) throws Exception {
       return proxyForwardWithKafkaHelper.forward(proxy,params, HttpMethodEnum.GET);

    }

    @PostMapping(value = "/**", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<byte[]>> proxyPostJson(ProxyExchange<byte[]> proxy, @RequestBody String params) throws Exception {
        return proxyForwardWithKafkaHelper.forward(proxy,params, HttpMethodEnum.POST_JSON);
    }

    /**
     * webflux 没有像springmvc 直接提供@RequestParam对post请求的表单参数进行支持
     * @param proxy
     * @param exchange
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/**")
    public Mono<ResponseEntity<byte[]>> proxyFormDataPost(ProxyExchange<byte[]> proxy, ServerWebExchange exchange) throws Exception {
        return exchange.getMultipartData().flatMap(formData -> {
            Map<String,Object> params = new HashMap<>();
            Map<String, Part> singleValueMap = formData.toSingleValueMap();
            singleValueMap.forEach((key,value) -> value.content().subscribe(dataBuffer -> params.put(key, DataBufferUtil.dataBufferToString(dataBuffer))));
            return proxyForwardWithKafkaHelper.forward(proxy,params, HttpMethodEnum.POST);
        });
    }


    /**
     * webflux 没有像springmvc 直接提供@RequestParam对post请求的表单参数进行支持
     * @param proxy
     * @param exchange
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/**",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<ResponseEntity<byte[]>> proxyFormUrlencodedPost(ProxyExchange<byte[]> proxy, ServerWebExchange exchange) throws Exception {
        return exchange.getFormData().flatMap(formData -> {
            Map<String,Object> params = new HashMap<>();
            formData.forEach((key,value) -> {
                params.put(key,value.get(0));
            });
            return proxyForwardWithKafkaHelper.forward(proxy,params, HttpMethodEnum.POST);
        });
    }





}

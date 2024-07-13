package com.github.lybgeek.gw.controller;


import cn.hutool.core.util.URLUtil;
import com.github.lybgeek.gw.util.MapToUrlParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProxyController {

    @Value("${remote.home:localhost:30001}")
    private URI home;


    @RequestMapping(value = "/**")
    public Mono<ResponseEntity<byte[]>> proxyGet(ProxyExchange<byte[]> proxy, ServerWebExchange exchange) throws Exception {
        String path = proxy.path();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        if(!queryParams.isEmpty()){
            path = path + "?" + MapToUrlParams.mapToUrlParams(queryParams.toSingleValueMap());
        }

        String url = URLUtil.normalize(home.toString() + "/" + path).replace("/api", "").trim();
        log.info(">>>>>>>>>>>>>> reqUrl:{}",url);
        return proxy.uri(url).forward();
    }


}

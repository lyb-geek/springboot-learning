package com.github.lybgeek.gateway.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.URLUtil;

import org.springframework.http.HttpCookie;
import org.springframework.util.*;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;


public final class HttpRequestParserUtils {

    public static final String SPILTE_COMMA = ",";


    public static final String SEPARATOR = "/";

    public static final String COMMA = ".";

    private static AntPathMatcher antPathMatcher = new AntPathMatcher();


    private HttpRequestParserUtils() {
    }


    public static String parse(String paramKey, ServerWebExchange exchange) {
        AtomicReference<String> param = new AtomicReference<>(exchange.getRequest().getHeaders().getFirst(paramKey));
        if (StringUtils.isEmpty(param.get())) {
            param.set(exchange.getRequest().getQueryParams().getFirst(paramKey));
        }

        if (StringUtils.isEmpty(param.get())) {
            MultiValueMap<String, HttpCookie> cookieMultiValueMap = exchange.getRequest().getCookies();
            if (!ObjectUtils.isEmpty(cookieMultiValueMap)) {
                cookieMultiValueMap.forEach((key, httpCookies) -> {
                    for (HttpCookie cookie : httpCookies) {
                        if (paramKey.equals(cookie.getName())) {
                            param.set(cookie.getValue());
                        }
                    }
                });
            }

        }

        return param.get();
    }


    public static boolean hasKey(String paramKey, ServerWebExchange exchange) {
        boolean isExistKey = exchange.getRequest().getHeaders().containsKey(paramKey);
        if (!isExistKey) {
            isExistKey = exchange.getRequest().getQueryParams().containsKey(paramKey);
            if (!isExistKey) {
                isExistKey = exchange.getRequest().getCookies().containsKey(paramKey);
            }
        }

        return isExistKey;
    }


    public static String getServiceName(ServerWebExchange exchange) {
        LinkedHashSet serviceNameUrls = exchange.getRequiredAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        if (!CollectionUtils.isEmpty(serviceNameUrls)) {
            for (Object serviceNameUrl : serviceNameUrls) {
                String url = serviceNameUrl.toString();
                if(url.contains(SEPARATOR)){
                    String[] urlArr = StringUtils.split(URLUtil.getPath(url),SEPARATOR);
                    if(ArrayUtil.isNotEmpty(urlArr)){
                        return urlArr[0];
                    }
                }
            }

        }
        return null;
    }

    private static boolean matchServiceName(ServerWebExchange exchange, String targetServiceName) {
        LinkedHashSet serviceNameUrls = exchange.getRequiredAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        if (!CollectionUtils.isEmpty(serviceNameUrls)) {
            for (Object serviceNameUrl : serviceNameUrls) {
                String url = serviceNameUrl.toString();
                if (url.contains(targetServiceName)) {
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean matchServiceNames(List<String> targetServiceNames,ServerWebExchange exchange){
        if(!CollectionUtils.isEmpty(targetServiceNames)){
            return targetServiceNames.stream().anyMatch(targetService -> matchServiceName(exchange,targetService));
        }

        return false;
    }

    public static boolean matchUrls(List<String> patternUrls, ServerWebExchange exchange) {
        String targetUrl = exchange.getRequest().getURI().getPath();
        return matchUrls(patternUrls, targetUrl);
    }

    public static boolean matchUrl(String patternUrl, ServerWebExchange exchange) {
        String targetUrl = exchange.getRequest().getURI().getPath();
        List<String> patternUrls = Arrays.asList(patternUrl);
        return matchUrls(patternUrls, targetUrl);
    }

    public static boolean matchUrls(List<String> patternUrls, String targetUrl) {
        if (CollectionUtils.isEmpty(patternUrls)) {
            return false;
        }

        return patternUrls.stream().anyMatch(patternUrl -> antPathMatcher.match(patternUrl, targetUrl));
    }




    public static Map<String, Object> convertQueryParams2Map(ServerWebExchange exchange) {
        Map<String, Object> queryMap = new LinkedHashMap<>();

        if(MapUtil.isNotEmpty(exchange.getRequest().getQueryParams())){
            exchange.getRequest().getQueryParams().forEach((key,value)->{
                queryMap.put(key,value.get(0));
            });
        }


        return queryMap;
    }






}
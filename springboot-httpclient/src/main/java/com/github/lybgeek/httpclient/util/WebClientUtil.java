package com.github.lybgeek.httpclient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 参考 https://blog.csdn.net/lkw411526/article/details/79766686
 */
@Component
public class WebClientUtil {

    private  final MediaType MEDIATYPE_JSON = MediaType.APPLICATION_JSON_UTF8;

    private  final MediaType MEDIATYPE_FORM = MediaType.APPLICATION_FORM_URLENCODED;

    @Autowired
    private WebClient webClient;

    /**
     *
     * @param paramter
     *            请求参数
     * @param url
     *            请求路径
     * @param resultType
     *            返回结果类型
     * @return
     */
    public  <T> T post(Object paramter, String url, Class<T> resultType) {

        return post(uri(url, HttpMethod.POST), paramter, resultType);
    }

    /**
     *
     * @param paramter
     *            请求参数
     * @param url
     *            请求路径
     * @param header
     *            请求头
     * @param resultType
     *            返回结果类型
     * @return
     */
    public  <T> T post(Object paramter, String url, Map<String, String> header, Class<T> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.POST);
        addHeader(header, uri);
        return post(uri, paramter, resultType);
    }

    private  <T> T post(RequestBodySpec uri, Object paramter, Class<T> resultType) {

        return uri.contentType(MEDIATYPE_JSON).body(Mono.just(paramter), Object.class).retrieve().bodyToMono(resultType)
                .block();
    }

    /**
     *
     * @param paramter
     *            请求参数
     * @param url
     *            请求路径
     * @param header
     *            请求头
     * @param resultType
     *            返回结果类型
     * @return
     */
    public  <T> T postForm(Map<String,String> paramter, String url, Map<String, String> header, Class<T> resultType) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.setAll(paramter);
        RequestBodySpec uri = uri(url, HttpMethod.POST);
        addHeader(header, uri);
        return postForm(uri, formData, resultType);
    }

    private  <T> T postForm(RequestBodySpec uri, MultiValueMap<String, String> formData , Class<T> resultType) {

        return uri.contentType(MEDIATYPE_FORM).body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(resultType)
                .block();
    }

    /**
     *
     * @param url
     *            请求路径
     * @param resultType
     *            返回结果类型
     * @return
     */
    public  <T> T get(String url, Class<T> resultType) {

        return uri(url, HttpMethod.GET).retrieve().bodyToMono(resultType).block();
    }

    /**
     *
     * @param url
     *            请求路径
     * @param header
     *            请求头
     * @param resultType
     *            返回结果类型
     * @return
     */
    public  <T> T get(String url, Map<String, String> header, Class<T> resultType) {

        RequestBodySpec uri = uri(url, HttpMethod.GET);
        addHeader(header, uri);
        return uri.retrieve().bodyToMono(resultType).block();
    }

    private  RequestBodySpec uri(String url, HttpMethod method) {

        return webClient.method(method).uri(url);
    }

    private  void addHeader(Map<String, String> header, RequestBodySpec uri) {

        if (!CollectionUtils.isEmpty(header)) {
            header.forEach((name, value) -> uri.cookie(name, value));
        }
    }

}

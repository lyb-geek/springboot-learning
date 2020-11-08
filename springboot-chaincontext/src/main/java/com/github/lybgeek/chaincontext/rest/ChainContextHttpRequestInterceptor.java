package com.github.lybgeek.chaincontext.rest;

import com.github.lybgeek.chaincontext.ChainContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


/**
 * HttpRequestInterceptor for chain context
 * 
 * @author linyb
 *
 */
public class ChainContextHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        if (ChainContextHolder.getCurrentContext() != null) {
            ChainContextHolder.getCurrentContext().forEach((key, value) -> {
                request.getHeaders().add(key, value.toString());
            });
        }
        return execution.execute(request, body);
    }

}

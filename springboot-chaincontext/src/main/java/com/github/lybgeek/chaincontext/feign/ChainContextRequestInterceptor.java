package com.github.lybgeek.chaincontext.feign;


import com.github.lybgeek.chaincontext.ChainContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * RequestInterceptor to add chain context to feign request
 * 
 * @author linyb
 *
 */
public class ChainContextRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        if (ChainContextHolder.getCurrentContext() != null) {
            ChainContextHolder.getCurrentContext().forEach((key, value) -> {
                template.header(key, value.toString());
            });
        }
    }

}

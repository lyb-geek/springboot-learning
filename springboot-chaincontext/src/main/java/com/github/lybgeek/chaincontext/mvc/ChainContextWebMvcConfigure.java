package com.github.lybgeek.chaincontext.mvc;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer of chaincontext
 * 
 * @author linyb
 */
public class ChainContextWebMvcConfigure implements WebMvcConfigurer {

    private ChainContexthandlerInterceptor chainContexthandlerInterceptor;

    public ChainContextWebMvcConfigure(ChainContexthandlerInterceptor chainContexthandlerInterceptor) {
        this.chainContexthandlerInterceptor = chainContexthandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(chainContexthandlerInterceptor).addPathPatterns("/**");
    }
}

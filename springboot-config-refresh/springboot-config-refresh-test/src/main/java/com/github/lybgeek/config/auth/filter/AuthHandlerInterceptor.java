package com.github.lybgeek.config.auth.filter;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.config.auth.exception.AuthException;
import com.github.lybgeek.config.auth.property.AuthProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.lybgeek.config.auth.controller.AuthLoggerChangeController.BASE_LOG_URL;

@Slf4j
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthProperty authProperty;

    @Autowired
    private WebEndpointProperties webEndpointProperties;
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public static final String MOCK_TOKEN_VALUE = "123456";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(log.isDebugEnabled()){
            log.debug("url:{},queryString:{}",request.getRequestURI(),request.getQueryString());
        }
        if(!authProperty.isEnabled()){
            return true;
        }
        if(isWhiteList(request)){
            return true;
        }

        String token = request.getHeader(authProperty.getTokenKey());
        if(MOCK_TOKEN_VALUE.equals(token)){
            return true;
        }

        throw new AuthException("token is not valid:" + token, HttpStatus.UNAUTHORIZED.name());
    }

    private boolean isWhiteList(HttpServletRequest request) {
        String url = request.getRequestURI();
        if(CollectionUtil.isNotEmpty(authProperty.getWhitelistUrls())){
            for (String whitelistUrl : authProperty.getWhitelistUrls()) {
               boolean isMatch = isMatch(whitelistUrl,url);
               if(isMatch){
                   return true;
                }
            }
        }
        boolean isMatchLogger = isMatch("/"+BASE_LOG_URL + "/**",url);
        if(isMatchLogger){
            return true;
        }
        return isMatch(webEndpointProperties.getBasePath() + "/**",url);
    }

    private boolean isMatch(String pattern, String url){
        if(antPathMatcher.match(pattern,url)){
            if(log.isDebugEnabled()){
                log.debug("url: {} is in whitelist",url);
            }
            return true;
        }
        return false;
    }
}

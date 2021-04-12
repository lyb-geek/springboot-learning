package com.github.lybgeek.sentinel.authority;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 解析访问来源，用于授权规则--黑白名单。
 * 当要进行授权规则时，则必须配置RequestOriginParser，否则授权规则无法生效
 *
 **/
@Component
public class CustomRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String origin = request.getParameter("origin");
        if(!StringUtils.isEmpty(origin)){
            //根据接口是否携带origin参数，如果携带参数为origin=pc，
            // 且sentinel-dashbord授权规则，来源设置为pc时，则表示要对请求来源为pc，进行黑白名单配置

            return origin;
        }
        //如果没请求参数接口没有携带，则表示按ip进行黑白名单设置
        return request.getRemoteAddr();
    }
}


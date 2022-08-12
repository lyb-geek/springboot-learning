package com.github.lybgeek.interceptor.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.constant.Constant;
import com.github.lybgeek.interceptor.CustomHttpServletRequestWrapper;
import com.github.lybgeek.model.Order;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class RequestBodyUtil {

    private RequestBodyUtil(){}

    public static void fillBodyWithId(CustomHttpServletRequestWrapper customHttpServletRequestWrapper){
        String body = customHttpServletRequestWrapper.getBody();
        if(JSONUtil.isJson(body)){
            Order order = JSON.parseObject(body, Order.class);
            if(ObjectUtil.isNotEmpty(order) && StringUtils.isBlank(order.getId())){
                String id = ((HttpServletRequest)customHttpServletRequestWrapper.getRequest()).getHeader(Constant.HEADER_KEY_ID);
                order.setId(id);

                String newBody = JSON.toJSONString(order);
                customHttpServletRequestWrapper.setBody(newBody);
                System.out.println(">>>>>>>>>>>>> newBody----> " + newBody);
            }
        }

    }
}

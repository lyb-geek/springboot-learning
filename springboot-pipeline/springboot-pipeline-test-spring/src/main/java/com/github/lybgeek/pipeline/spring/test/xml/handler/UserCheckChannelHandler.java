package com.github.lybgeek.pipeline.spring.test.xml.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.test.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
public class UserCheckChannelHandler extends AbstactChannelHandler {

    
    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("XML------------------------------------步骤一：用户数据校验【"+channelHandlerRequest.getRequestId()+"】");
        String json = JSON.toJSONString(channelHandlerRequest.getParams());
        List<User> users = JSON.parseArray(json,User.class);
        if(CollectionUtil.isEmpty(users) || StringUtils.isBlank(users.get(0).getFullname())){
            log.error("用户名不能为空");
            return false;
        }
        return true;


    }
}

package com.github.lybgeek.pipeline.spring.test.anotataion.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.annotation.Pipeline;
import com.github.lybgeek.pipeline.spring.test.anotataion.service.UserService;
import com.github.lybgeek.pipeline.spring.test.model.User;

import java.util.List;
import java.util.Map;

@Pipeline(consumePipelinesService = UserService.class,consumePipelinesMethod = "save",args = {User.class},order = 5)
public class UserPrintChannleHandler extends AbstactChannelHandler {
    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("------------------------------------步骤五：打印用户数据【"+channelHandlerRequest.getRequestId()+"】");
        String json = JSON.toJSONString(channelHandlerRequest.getParams());
        List<User> users = JSON.parseArray(json,User.class);
        if(CollectionUtil.isNotEmpty(users)){
            User user = users.get(0);
            Object userMap = chx.get("userMap");
            if(userMap instanceof Map){
                Map map = (Map)userMap;
                if(map.containsKey(user.getUsername())){
                    System.out.println(map.get(user.getUsername()));
                    return true;
                }
            }
        }

        return false;
    }
}

package com.github.lybgeek.pipeline.test.handler;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.test.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserPrintChannleHandler extends AbstactChannelHandler {
    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("------------------------------------步骤五：打印用户数据【"+channelHandlerRequest.getRequestId()+"】");
        Object params = channelHandlerRequest.getParams();
        if(params instanceof User){
            Object userMap = chx.get("userMap");
            if(userMap instanceof Map){
                Map map = (Map)userMap;
                if(map.containsKey(((User) params).getUsername())){
                    System.out.println(map.get(((User) params).getUsername()));
                    return true;
                }
            }
        }

        return false;
    }
}

package com.github.lybgeek.pipeline.spring.test.yml.handler;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.test.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMockSaveChannelHandler extends AbstactChannelHandler {

    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("yml------------------------------------步骤四：模拟用户数据落库【"+channelHandlerRequest.getRequestId()+"】");
        Object params = channelHandlerRequest.getParams();
        if(params instanceof User){
            Map<String, User> userMap = new HashMap<>();
            User user = (User)params;
            userMap.put(user.getUsername(),user);
            chx.put("userMap",userMap);
            return true;
        }


        return false;
    }
}

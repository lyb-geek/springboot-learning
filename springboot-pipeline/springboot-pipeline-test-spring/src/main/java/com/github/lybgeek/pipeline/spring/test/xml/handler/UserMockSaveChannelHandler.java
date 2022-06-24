package com.github.lybgeek.pipeline.spring.test.xml.handler;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.test.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMockSaveChannelHandler extends AbstactChannelHandler {

    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("XML------------------------------------步骤四：模拟用户数据落库【"+channelHandlerRequest.getRequestId()+"】");
        String json = JSON.toJSONString(channelHandlerRequest.getParams());
        List<User> users = JSON.parseArray(json,User.class);
        if(CollectionUtil.isNotEmpty(users)){
            User user = users.get(0);
            Map<String, User> userMap = new HashMap<>();
            userMap.put(user.getUsername(),user);
            chx.put("userMap",userMap);
            return true;
        }


        return false;
    }
}

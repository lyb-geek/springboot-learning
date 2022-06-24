package com.github.lybgeek.pipeline.spring.test.anotataion.handler;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.annotation.Pipeline;
import com.github.lybgeek.pipeline.spring.test.anotataion.service.UserService;
import com.github.lybgeek.pipeline.spring.test.model.User;

import java.util.List;

@Pipeline(consumePipelinesService = UserService.class,consumePipelinesMethod = "save",args = {User.class},order = 3)
public class UserPwdEncryptChannelHandler extends AbstactChannelHandler {
    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("------------------------------------步骤三：用户密码明文转密文【"+channelHandlerRequest.getRequestId()+"】");
        String json = JSON.toJSONString(channelHandlerRequest.getParams());
        List<User> users = JSON.parseArray(json,User.class);
        if(CollectionUtil.isNotEmpty(users)){
            User user = users.get(0);
            String encryptPwd = DigestUtil.sha256Hex(user.getPassword());
            user.setPassword(encryptPwd);
            return true;
        }

        return false;
    }
}

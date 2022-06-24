package com.github.lybgeek.pipeline.spring.test.yml.handler;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.test.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class UserCheckChannelHandler extends AbstactChannelHandler {

    
    @Override
    public boolean handler(ChannelHandlerContext chx) {
        ChannelHandlerRequest channelHandlerRequest = chx.getChannelHandlerRequest();
        System.out.println("yml------------------------------------步骤一：用户数据校验【"+channelHandlerRequest.getRequestId()+"】");
        Object params = channelHandlerRequest.getParams();
        if(params instanceof User){
            User user = (User)params;
            if(StringUtils.isBlank(user.getFullname())){
                log.error("用户名不能为空");
                return false;
            }
            return true;
        }


        return false;
    }
}

package com.github.lybgeek.pipeline;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class ChannelPipeline {

    private LinkedBlockingDeque<AbstactChannelHandler> channelHandlers = new LinkedBlockingDeque();

    private ChannelHandlerContext handlerContext;


    public ChannelPipeline addFirst(AbstactChannelHandler channelHandler){
       return addFirst(null,channelHandler);
    }

    public ChannelPipeline addLast(AbstactChannelHandler channelHandler){
      return addLast(null,channelHandler);
    }

    public ChannelPipeline addFirst(String channelHandlerName,AbstactChannelHandler channelHandler){
        if(StringUtils.isNotBlank(channelHandlerName)){
            channelHandler.setChannelHandlerName(channelHandlerName);
        }
        channelHandlers.addFirst(channelHandler);
        return this;
    }

    public ChannelPipeline addLast(String channelHandlerName,AbstactChannelHandler channelHandler){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(channelHandlerName)){
            channelHandler.setChannelHandlerName(channelHandlerName);
        }
        channelHandlers.addLast(channelHandler);
        return this;
    }


    public void setChannelHandlers(LinkedBlockingDeque<AbstactChannelHandler> channelHandlers) {
        this.channelHandlers = channelHandlers;
    }

    public ChannelHandlerContext getHandlerContext() {
        return handlerContext;
    }

    public void setHandlerContext(ChannelHandlerContext handlerContext) {
        this.handlerContext = handlerContext;
    }

    public boolean start(ChannelHandlerRequest channelHandlerRequest){
         if(channelHandlers.isEmpty()){
             log.warn("channelHandlers is empty");
             return false;
         }

        return handler(channelHandlerRequest);
    }

    private boolean handler(ChannelHandlerRequest channelHandlerRequest) {
        if(StringUtils.isBlank(channelHandlerRequest.getRequestId())){
            channelHandlerRequest.setRequestId(String.valueOf(SnowflakeUtils.getNextId()));
        }
        handlerContext.put(ChannelHandlerContext.CHANNEL_HANDLER_REQUEST_KEY,channelHandlerRequest);
        boolean isSuccess = true;
        try {
            for (AbstactChannelHandler channelHandler : channelHandlers) {
                  isSuccess = channelHandler.handler(handlerContext);
                if(!isSuccess){
                    break;
                }
            }

            if(!isSuccess){
                channelHandlers.clear();
            }
        } catch (Exception e) {
            log.error("{}",e.getMessage());
            isSuccess = false;
        } finally {
            handlerContext.release();
        }
        return isSuccess;
    }

}

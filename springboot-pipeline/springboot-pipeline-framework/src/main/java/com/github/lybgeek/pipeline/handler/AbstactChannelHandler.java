package com.github.lybgeek.pipeline.handler;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;

public abstract class AbstactChannelHandler {

    private String channelHandlerName;

    public String getChannelHandlerName() {
        return channelHandlerName;
    }

    public void setChannelHandlerName(String channelHandlerName) {
        this.channelHandlerName = channelHandlerName;
    }

    public abstract boolean handler(ChannelHandlerContext chx);


}

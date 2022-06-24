package com.github.lybgeek.pipeline;


import com.github.lybgeek.pipeline.context.ChannelHandlerContext;

public class ChannelPipelineExecutor {

   public static ChannelPipeline pipeline(){
       ChannelHandlerContext ctx = ChannelHandlerContext.getCurrentContext();
       ChannelPipeline channelPipeline = new ChannelPipeline();
       channelPipeline.setHandlerContext(ctx);
       return channelPipeline;

   }

}

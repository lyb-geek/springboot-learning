package com.github.lybgeek.pipeline.context;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelHandlerContext extends ConcurrentHashMap<String,Object> {

    protected static Class<? extends ChannelHandlerContext> contextClass = ChannelHandlerContext.class;

    protected static final TransmittableThreadLocal<? extends ChannelHandlerContext> CHAIN_CONTEXT = new TransmittableThreadLocal<ChannelHandlerContext>() {
        @Override
        protected ChannelHandlerContext initialValue() {
            try {
                return contextClass.getDeclaredConstructor().newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    };

    /**
     * 覆盖默认的链路上下文
     *
     * @param clazz
     */
    public static void setContextClass(Class<? extends ChannelHandlerContext> clazz) {
        contextClass = clazz;
    }

    /**
     * 获取当前链路上下文
     *
     *
     */
    public static final ChannelHandlerContext getCurrentContext() {
        return CHAIN_CONTEXT.get();
    }

    /**
     * 释放上下文资源
     *
     * @return
     */
    public void release() {
        this.clear();
        CHAIN_CONTEXT.remove();
    }

    /**
     *
     * 获取上下文默认值
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getDefault(String key, Object defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

    public static final String CHANNEL_HANDLER_REQUEST_KEY = "channelHandlerRequest";

    public ChannelHandlerRequest getChannelHandlerRequest() {
        return (ChannelHandlerRequest) this.getDefault(CHANNEL_HANDLER_REQUEST_KEY,ChannelHandlerRequest.builder().build());
    }


}

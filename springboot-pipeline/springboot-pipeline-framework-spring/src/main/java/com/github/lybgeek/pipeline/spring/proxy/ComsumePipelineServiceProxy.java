package com.github.lybgeek.pipeline.spring.proxy;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.pipeline.ChannelPipeline;
import com.github.lybgeek.pipeline.ChannelPipelineExecutor;
import com.github.lybgeek.pipeline.model.ChannelHandlerRequest;
import com.github.lybgeek.pipeline.spring.model.HandlerInvotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class ComsumePipelineServiceProxy implements InvocationHandler {
    
    private List<HandlerInvotation> handlerInvotations;

    public ComsumePipelineServiceProxy(List<HandlerInvotation> handlerInvotations) {
        this.handlerInvotations = handlerInvotations;
    }

    public Object getInstance(Class pipelineServiceClz){
        return Proxy.newProxyInstance(pipelineServiceClz.getClassLoader(),new Class[]{pipelineServiceClz},this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        List<HandlerInvotation> sortPipelineHandlers = sortPipelineHandlers(matchPipelineHandlers(method));
        return handler(sortPipelineHandlers,args);
    }

    private List<HandlerInvotation> matchPipelineHandlers(Method method) {
        List<HandlerInvotation> matchHandlerInvotations = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(handlerInvotations)){
            for (HandlerInvotation handlerInvotation : handlerInvotations) {
                String proxyMethod = handlerInvotation.getConsumePipelinesMethod() + Arrays.toString(handlerInvotation.getArgs());
                String targetMethod = method.getName() + Arrays.toString(method.getParameterTypes());
                if(targetMethod.equals(proxyMethod)){
                    matchHandlerInvotations.add(handlerInvotation);
                }

            }
        }
        return matchHandlerInvotations;
    }


    private List<HandlerInvotation> sortPipelineHandlers(List<HandlerInvotation> matchHandlerInvotations){
        List<HandlerInvotation> sortPipelineHandlers = matchHandlerInvotations.stream().sorted(Comparator.comparing(HandlerInvotation::getOrder)).collect(Collectors.toList());
        return Collections.unmodifiableList(sortPipelineHandlers);
    }

    private boolean handler(List<HandlerInvotation> sortPipelineHandlers,Object[] args){
        ChannelPipeline pipeline = ChannelPipelineExecutor.pipeline();
        for (HandlerInvotation sortPipelineHandler : sortPipelineHandlers) {
            pipeline.addLast(sortPipelineHandler.getHandler());
        }
        return pipeline.start(ChannelHandlerRequest.builder().params(args).build());
    }


}

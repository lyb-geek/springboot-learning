package com.github.lybgeek.pipeline.spring.factory;

import cn.hutool.core.map.MapUtil;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.spring.annotation.Pipeline;
import com.github.lybgeek.pipeline.spring.model.HandlerInvotation;
import com.github.lybgeek.pipeline.spring.proxy.ComsumePipelineServiceProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


public class ComsumePipelineFactoryBean implements FactoryBean, ApplicationContextAware {

    private Class pipelineServiceClz;


    private List<HandlerInvotation> handlerInvotations = new CopyOnWriteArrayList<>();

    private boolean createByXml;



    @Override
    public Object getObject() throws Exception {
        return new ComsumePipelineServiceProxy(handlerInvotations).getInstance(pipelineServiceClz);
    }

    @Override
    public Class<?> getObjectType() {
        return pipelineServiceClz;
    }


    public Class getPipelineServiceClz() {
        return pipelineServiceClz;
    }

    public void setPipelineServiceClz(Class pipelineServiceClz) {
        this.pipelineServiceClz = pipelineServiceClz;
    }

    public boolean isCreateByXml() {
        return createByXml;
    }

    public void setCreateByXml(boolean createByXml) {
        this.createByXml = createByXml;
    }

    public List<HandlerInvotation> getHandlerInvotations() {
        return handlerInvotations;
    }

    public void setHandlerInvotations(List<HandlerInvotation> handlerInvotations) {
        this.handlerInvotations = handlerInvotations;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(!createByXml){
            this.initPipelineHandlers(applicationContext);
        }

    }

    private void initPipelineHandlers(ApplicationContext applicationContext){
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Pipeline.class);
        if(MapUtil.isNotEmpty(map)){
            for (Object channelHandler : map.values()) {
                if(channelHandler instanceof AbstactChannelHandler){
                    AbstactChannelHandler handler = (AbstactChannelHandler)channelHandler;
                    Pipeline pipeline = handler.getClass().getAnnotation(Pipeline.class);
                    if(pipeline.consumePipelinesService().isAssignableFrom(pipelineServiceClz)){
                        HandlerInvotation invotation = HandlerInvotation.builder()
                                .args(pipeline.args())
                                .handler(handler)
                                .order(pipeline.order())
                                .consumePipelinesMethod(pipeline.consumePipelinesMethod())
                                .build();
                        handlerInvotations.add(invotation);
                    }

                }


            }
        }

    }
}

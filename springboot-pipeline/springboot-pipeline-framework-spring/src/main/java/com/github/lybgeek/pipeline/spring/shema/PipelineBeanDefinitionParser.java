package com.github.lybgeek.pipeline.spring.shema;


import cn.hutool.core.util.ArrayUtil;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.spring.exception.PipelineException;
import com.github.lybgeek.pipeline.spring.factory.ComsumePipelineFactoryBean;
import com.github.lybgeek.pipeline.spring.model.HandlerInvotation;
import com.github.lybgeek.pipeline.spring.shema.model.PipelineConfig;
import com.github.lybgeek.pipeline.spring.shema.model.PipelineHandlerConfig;
import com.github.lybgeek.pipeline.spring.shema.util.BeanUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;
import static org.springframework.util.StringUtils.trimArrayElements;

public class PipelineBeanDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        PipelineConfig pipelineConfig = buildPipelineConfig(element);
        List<HandlerInvotation> handlerInvotations = this.buildHandlerInvotations(pipelineConfig);
        GenericBeanDefinition beanDefinition = getGenericBeanDefinition(element, parserContext, pipelineConfig, handlerInvotations);
        return beanDefinition;
    }

    private GenericBeanDefinition getGenericBeanDefinition(Element element, ParserContext parserContext, PipelineConfig pipelineConfig, List<HandlerInvotation> handlerInvotations) {
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.getPropertyValues().addPropertyValue("pipelineServiceClz",pipelineConfig.getConsumePipelinesService());
        beanDefinition.getPropertyValues().addPropertyValue("handlerInvotations",handlerInvotations);
        beanDefinition.getPropertyValues().addPropertyValue("createByXml",true);
        beanDefinition.setBeanClass(ComsumePipelineFactoryBean.class);
        String beanName = BeanUtils.generateBeanName(element,"id",parserContext,pipelineConfig.getConsumePipelinesService().getSimpleName());
        parserContext.getRegistry().registerBeanDefinition(beanName,beanDefinition);
        return beanDefinition;
    }

    @SneakyThrows
    private List<HandlerInvotation> buildHandlerInvotations(PipelineConfig pipelineConfig){
        List<HandlerInvotation> handlerInvotations = new ArrayList<>();
        for (PipelineHandlerConfig pipelineHandlerConfig : pipelineConfig.getPipelineChain()) {
            if(!AbstactChannelHandler.class.isAssignableFrom(pipelineHandlerConfig.getPipelineClass())){
                throw new PipelineException("pipelineHandler className must be 【com.github.lybgeek.pipeline.handler.AbstactChannelHandler】 subclass");
            }

            AbstactChannelHandler channelHandler = (AbstactChannelHandler) pipelineHandlerConfig.getPipelineClass().getDeclaredConstructor().newInstance();
            HandlerInvotation invotation = HandlerInvotation.builder()
                    .args(pipelineConfig.getArgs())
                    .handler(channelHandler)
                    .order(pipelineHandlerConfig.getOrder())
                    .consumePipelinesMethod(pipelineConfig.getConsumePipelinesMethod())
                    .build();
            handlerInvotations.add(invotation);

        }
        return handlerInvotations;
    }

    @SneakyThrows
    private PipelineConfig buildPipelineConfig(Element element){
        String argsType = element.getAttribute("argsType");
        String[] argsTypeArr = trimArrayElements(commaDelimitedListToStringArray(argsType));
        String consumePipelinesMethod = element.getAttribute("consumePipelinesMethod");
        String consumePipelinesServiceClassName = element.getAttribute("consumePipelinesServiceClassName");


        Class[] args = null;
        if(ArrayUtil.isNotEmpty(argsTypeArr)){
            args = new Class[argsTypeArr.length];
            for (int i = 0; i < argsTypeArr.length; i++) {
                Class argType = Class.forName(argsTypeArr[i]);
                args[i] = argType;
            }
        }

        List<PipelineHandlerConfig> pipelineHandlerConfigs = buildPipelineHandlerConfig(element);

        return PipelineConfig.builder().args(args)
                .consumePipelinesMethod(consumePipelinesMethod)
                .consumePipelinesService(Class.forName(consumePipelinesServiceClassName))
                .pipelineChain(pipelineHandlerConfigs)
                .build();
    }

    @SneakyThrows
    private List<PipelineHandlerConfig> buildPipelineHandlerConfig(Element element){
        NodeList nodeList = element.getChildNodes();
        if (nodeList == null) {
            return Collections.emptyList();
        }

        List<PipelineHandlerConfig> pipelineHandlerConfigs = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i) instanceof Element)) {
                continue;
            }
            Element childElement = (Element) nodeList.item(i);
            if ("pipelineHandler".equals(childElement.getNodeName()) || "pipelineHandler".equals(childElement.getLocalName())) {
                String pipelineHanlderClassName = childElement.getAttribute("className");
                String pipelineHanlderOrder = childElement.getAttribute("order");
                Class pipelineHanlderClass = Class.forName(pipelineHanlderClassName);
                PipelineHandlerConfig pipelineHandlerConfig = PipelineHandlerConfig.builder()
                        .PipelineClass(pipelineHanlderClass)
                        .order(Integer.valueOf(pipelineHanlderOrder))
                        .build();
                pipelineHandlerConfigs.add(pipelineHandlerConfig);
            }
        }

        return pipelineHandlerConfigs;
    }
}

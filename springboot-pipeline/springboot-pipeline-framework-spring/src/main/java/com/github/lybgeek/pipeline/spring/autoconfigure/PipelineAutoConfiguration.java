package com.github.lybgeek.pipeline.spring.autoconfigure;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.pipeline.ChannelPipeline;
import com.github.lybgeek.pipeline.context.ChannelHandlerContext;
import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import com.github.lybgeek.pipeline.spring.exception.PipelineException;
import com.github.lybgeek.pipeline.spring.model.PipelineDefinition;
import com.github.lybgeek.pipeline.spring.properties.PipelineDefinitionProperties;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
@EnableConfigurationProperties(PipelineDefinitionProperties.class)
public class PipelineAutoConfiguration implements BeanFactoryAware,InitializingBean, SmartInitializingSingleton {


    @Autowired
    private PipelineDefinitionProperties pipelineDefinitionProperties;

    private DefaultListableBeanFactory defaultListableBeanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;

    }

    private void registerPipeline(DefaultListableBeanFactory defaultListableBeanFactory, PipelineDefinition pipelineDefinition) {
        LinkedBlockingDeque linkedBlockingDeque = buildPipelineQuque(pipelineDefinition);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) BeanDefinitionBuilder.genericBeanDefinition(ChannelPipeline.class).getBeanDefinition();
        beanDefinition.getPropertyValues().addPropertyValue("channelHandlers",linkedBlockingDeque);
        defaultListableBeanFactory.registerBeanDefinition(PipelineDefinition.PREFIX + pipelineDefinition.getComsumePipelineName() ,beanDefinition);
    }

    @SneakyThrows
    private LinkedBlockingDeque buildPipelineQuque(PipelineDefinition pipelineDefinition) {
        List<String> pipelineClassNames = pipelineDefinition.getPipelineClassNames();
        if(CollectionUtil.isEmpty(pipelineClassNames)){
           throw new PipelineException("pipelineClassNames must config");
        }

        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        for (String pipelineClassName : pipelineClassNames) {
            Class<?> pipelineClassClass = Class.forName(pipelineClassName);
            if(!AbstactChannelHandler.class.isAssignableFrom(pipelineClassClass)){
                throw new PipelineException("pipelineClassNames must be 【com.github.lybgeek.pipeline.handler.AbstactChannelHandler】 subclass");
            }
            Object pipeline = pipelineClassClass.getDeclaredConstructor().newInstance();
            linkedBlockingDeque.addLast(pipeline);
        }

        return linkedBlockingDeque;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(CollectionUtil.isNotEmpty(pipelineDefinitionProperties.getChain())){
            for (PipelineDefinition pipelineDefinition : pipelineDefinitionProperties.getChain()) {
                registerPipeline(defaultListableBeanFactory, pipelineDefinition);
            }
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, ChannelPipeline> pipelineBeanMap = defaultListableBeanFactory.getBeansOfType(ChannelPipeline.class);
        pipelineBeanMap.forEach((key,bean)->{
            bean.setHandlerContext(ChannelHandlerContext.getCurrentContext());
        });

    }
}

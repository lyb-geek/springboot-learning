package com.github.lybgeek.pipeline.spring.shema;


import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class PipelineNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
       registerBeanDefinitionParser("pipeline",new PipelineBeanDefinitionParser());
    }
}

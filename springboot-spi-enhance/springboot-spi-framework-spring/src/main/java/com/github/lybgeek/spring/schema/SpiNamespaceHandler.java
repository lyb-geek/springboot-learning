package com.github.lybgeek.spring.schema;

import com.github.lybgeek.spring.schema.factory.SpiAnnotationPostProcessor;
import com.github.lybgeek.spring.schema.parser.AnnotationBeanDefinitionParser;
import com.github.lybgeek.spring.schema.parser.InterceptorBeanDefinitionParser;
import com.github.lybgeek.spring.schema.parser.InterceptorChainBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *     自定义spring标签步骤
 * 　  1、NamespaceHandler实现类处理自定义标签的处理器
 * 　　2、自定义解析BeanDefinitionParser解析器
 * 　　3、自定义标签
 * 　　4、spring.handlers、spring.schemas中写入处理器、标签的位置
 **/
public class SpiNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("scan", new AnnotationBeanDefinitionParser(SpiAnnotationPostProcessor.class));
        registerBeanDefinitionParser("interceptor", new InterceptorBeanDefinitionParser());
        registerBeanDefinitionParser("interceptorChain", new InterceptorChainBeanDefinitionParser());
    }
}

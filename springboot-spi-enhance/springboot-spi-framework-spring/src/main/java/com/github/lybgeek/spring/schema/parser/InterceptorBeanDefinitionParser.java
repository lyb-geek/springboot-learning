package com.github.lybgeek.spring.schema.parser;

import com.github.lybgeek.spring.schema.util.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class InterceptorBeanDefinitionParser implements BeanDefinitionParser {



    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String beanClassName = element.getAttribute("class");

        RootBeanDefinition beanDefinition = registerInterceptor(element, parserContext, beanClassName);

        return beanDefinition;
    }

    public static RootBeanDefinition registerInterceptor(Element element, ParserContext parserContext, String beanClassName) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setLazyInit(false);
        String beanName = BeanUtils.generateBeanName(element,"id",parserContext,beanClassName);
        parserContext.getRegistry().registerBeanDefinition(beanName,beanDefinition);
        return beanDefinition;
    }


}

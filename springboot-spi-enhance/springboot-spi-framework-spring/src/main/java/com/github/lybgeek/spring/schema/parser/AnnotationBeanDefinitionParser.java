package com.github.lybgeek.spring.schema.parser;

import com.github.lybgeek.spring.schema.util.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import static org.springframework.util.StringUtils.commaDelimitedListToStringArray;
import static org.springframework.util.StringUtils.trimArrayElements;

public class AnnotationBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public AnnotationBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        String packageToScan = element.getAttribute("basePackages");
        String[] packagesToScan = trimArrayElements(commaDelimitedListToStringArray(packageToScan));

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0,packagesToScan);
        String beanName = BeanUtils.generateBeanName(element,"id",parserContext,beanClass.getName());
        parserContext.getRegistry().registerBeanDefinition(beanName,beanDefinition);

        return beanDefinition;
    }





}

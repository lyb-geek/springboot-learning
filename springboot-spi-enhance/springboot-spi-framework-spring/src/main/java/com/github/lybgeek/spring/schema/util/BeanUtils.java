package com.github.lybgeek.spring.schema.util;


import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public final class BeanUtils {

    private BeanUtils(){}

    public static String generateBeanName(Element element, String attributeName, ParserContext parserContext,String beanClassName){
        String configId = resolveAttribute(element, attributeName, parserContext);
        String beanName = configId;
        if (StringUtils.isEmpty(beanName)) {
            // generate bean name
            String prefix = beanClassName;
            int counter = 0;
            beanName = prefix + "#" + counter;
            while (parserContext.getRegistry().containsBeanDefinition(beanName)) {
                beanName = prefix + "#" + (counter++);
            }
        }
        return beanName;
    }

    public static String resolveAttribute(Element element, String attributeName, ParserContext parserContext) {
        String attributeValue = element.getAttribute(attributeName);
        return attributeValue;
    }
}

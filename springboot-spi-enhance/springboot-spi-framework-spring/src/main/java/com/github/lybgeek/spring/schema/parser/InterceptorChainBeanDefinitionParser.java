package com.github.lybgeek.spring.schema.parser;

import com.github.lybgeek.spring.schema.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InterceptorChainBeanDefinitionParser implements BeanDefinitionParser {

    public static final String ELEMENT_INTERCEPTOR = "interceptor";
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        parseInterceptors(element.getChildNodes(),parserContext);

        return null;
    }

    private void parseInterceptors(NodeList nodeList, ParserContext parserContext) {
        if (nodeList == null) {
            return;
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i) instanceof Element)) {
                continue;
            }
            Element element = (Element) nodeList.item(i);
            if (ELEMENT_INTERCEPTOR.equals(element.getNodeName()) || ELEMENT_INTERCEPTOR.equals(element.getLocalName())) {
                String interceptorClassName = BeanUtils.resolveAttribute(element, "class", parserContext);
                if (StringUtils.isEmpty(interceptorClassName)) {
                    throw new IllegalStateException("<spi:interceptor> class attribute == null");
                }

                InterceptorBeanDefinitionParser.registerInterceptor(element,parserContext,interceptorClassName);
            }
        }

    }




}

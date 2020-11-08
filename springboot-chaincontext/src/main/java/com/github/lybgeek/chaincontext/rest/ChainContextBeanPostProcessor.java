package com.github.lybgeek.chaincontext.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.client.RestTemplate;

/**
 * Postprocessor to add interceptor for restTemplate
 * 
 * @author linyb
 *
 */
public class ChainContextBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ChainContextBeanPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RestTemplate) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("add ChainContextHttpRequestInterceptor to RestTemplate");
            }
            RestTemplate restTemplate = RestTemplate.class.cast(bean);
            restTemplate.getInterceptors().add(new ChainContextHttpRequestInterceptor());
            return restTemplate;
        }
        return bean;
    }
}

package com.github.lybgeek.cor.autoconfigure;


import com.github.lybgeek.advise.autoconfigure.AdviseAutoConfiguration;
import com.github.lybgeek.cor.CorHandlerInterceptor;
import com.github.lybgeek.cor.chain.MethodInterceptorChain;
import com.github.lybgeek.cor.handler.AbstarctHandler;
import com.github.lybgeek.cor.handler.support.DefaultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

@Configuration
@AutoConfigureBefore(AdviseAutoConfiguration.class)
@Slf4j
public class CorHandlerInterceptorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CorHandlerInterceptor corHandlerInterceptor(ObjectProvider<List<AbstarctHandler>> provider){
        MethodInterceptorChain methodInterceptorChain = new MethodInterceptorChain();
        loadedHandlerBySpring(provider, methodInterceptorChain);
        loadedHanlderBySpi(methodInterceptorChain);
        CorHandlerInterceptor corHandlerInterceptor = new CorHandlerInterceptor();
        corHandlerInterceptor.setChain(methodInterceptorChain);
        return corHandlerInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultHandler defaultHandler(){
        return new DefaultHandler();
    }

    private void loadedHanlderBySpi(MethodInterceptorChain methodInterceptorChain) {
        ServiceLoader<AbstarctHandler> serviceLoader = ServiceLoader.load(AbstarctHandler.class);
        Iterator<AbstarctHandler> iterator = serviceLoader.iterator();
        while(iterator.hasNext()){
            AbstarctHandler abstarctHandler = iterator.next();
            log.info("load hander by spi -> 【{}】",abstarctHandler.getClass().getName());
            methodInterceptorChain.addHandler(abstarctHandler);
        }
    }


    private void loadedHandlerBySpring(ObjectProvider<List<AbstarctHandler>> provider, MethodInterceptorChain methodInterceptorChain) {
        List<AbstarctHandler> getListBySpring = provider.getIfAvailable();
        if(!CollectionUtils.isEmpty(getListBySpring)){
            for (AbstarctHandler abstarctHandler : getListBySpring) {
                log.info("load hander by spring -> 【{}】",abstarctHandler.getClass().getName());
                methodInterceptorChain.addHandler(abstarctHandler);
            }
        }
    }
}

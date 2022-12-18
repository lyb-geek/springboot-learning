package com.github.lybgeek.http.autoconfigure;


import com.github.lybgeek.http.HttpTemplate;
import com.github.lybgeek.http.HttpTemplateComposite;
import com.github.lybgeek.http.common.properties.HttpProperties;
import com.github.lybgeek.http.support.DefaultHttpTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HttpTemplateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpTemplate defaultHttpTemplate(){
        return new DefaultHttpTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpTemplateComposite httpTemplateComposite(HttpProperties httpProperties, ObjectProvider<List<HttpTemplate>> httpTemplates){
        List<HttpTemplate> list = httpTemplates.getIfAvailable();
        HttpTemplateComposite httpTemplateComposite = new HttpTemplateComposite(httpProperties);
        httpTemplateComposite.setHttpTemplates(list);
        return httpTemplateComposite;
    }
}

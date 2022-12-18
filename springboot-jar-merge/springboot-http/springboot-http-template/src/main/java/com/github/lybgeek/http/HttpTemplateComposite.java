package com.github.lybgeek.http;


import com.github.lybgeek.http.common.HttpMethod;
import com.github.lybgeek.http.common.exception.HttpException;
import com.github.lybgeek.http.common.properties.HttpProperties;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class HttpTemplateComposite {

    private List<HttpTemplate> httpTemplates = new ArrayList<>();

    private HttpProperties httpProperties;

    public HttpTemplateComposite(HttpProperties httpProperties) {
        this.httpProperties = httpProperties;
    }

    public Object execute(String url, HttpMethod httpMethod, Map<String, String> headerMap, Map<String,Object> paramMap){
          if(CollectionUtils.isEmpty(getHttpTemplates())){
              throw new HttpException("HTTP TEMPLATE IS NOT FOUND !");
          }

        for (HttpTemplate httpTemplate : getHttpTemplates()) {
            boolean supportType = httpTemplate.supportsSourceType(httpProperties.getHttpType());
            if(supportType){
                return httpTemplate.execute(url,httpMethod,headerMap,paramMap);
            }

        }
        return null;
    }

    public void addHttpTemplate(HttpTemplate ... httpTemplate){
        httpTemplates.addAll(Arrays.asList(httpTemplate));
    }

    public List<HttpTemplate> getHttpTemplates() {
        return Collections.unmodifiableList(httpTemplates);
    }

    public void setHttpTemplates(List<HttpTemplate> httpTemplates) {
        this.httpTemplates = httpTemplates;
    }

    public HttpProperties getHttpProperties() {
        return httpProperties;
    }
}

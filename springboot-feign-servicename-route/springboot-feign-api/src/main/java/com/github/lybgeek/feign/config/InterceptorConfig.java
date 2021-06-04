package com.github.lybgeek.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class InterceptorConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RequestInterceptor cloudContextInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String url = template.url();
                if (url.contains("$env")) {
                    url = url.replace("$env", route(template));
                    System.out.println(url);
                    template.uri(url);
                }
                if (url.startsWith("//")) {
                    url = "http:" + url;
                    template.target(url);
                    template.uri("");
                }


            }


            private CharSequence route(RequestTemplate template) {
                // TODO 你的路由算法在这里
                return environment.getProperty("feign.env");
            }
        };
    }

}

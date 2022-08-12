package com.github.lybgeek.resolver.autoconfigure;


import com.github.lybgeek.resolver.UserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HandlerMethodArgumentResolverAutoConfiguration implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;


    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodArgumentResolver> argumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> customArgumentResolvers = new ArrayList<>();

        for (HandlerMethodArgumentResolver argumentResolver : argumentResolvers) {
            if(argumentResolver instanceof RequestResponseBodyMethodProcessor){
                 customArgumentResolvers.add(new UserHandlerMethodArgumentResolver(argumentResolver));
            }
            customArgumentResolvers.add(argumentResolver);
        }

        requestMappingHandlerAdapter.setArgumentResolvers(customArgumentResolvers);

    }
}

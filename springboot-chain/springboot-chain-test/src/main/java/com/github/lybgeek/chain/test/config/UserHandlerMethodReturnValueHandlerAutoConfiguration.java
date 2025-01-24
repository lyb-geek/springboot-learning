package com.github.lybgeek.chain.test.config;

import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.chain.test.user.resovle.UserHandlerMethodReturnValueHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserHandlerMethodReturnValueHandlerAutoConfiguration implements InitializingBean {

    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private final UserHandlerMethodReturnValueHandler userHandlerMethodReturnValueHandler;
    

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> customerReturnValueHandlers = new ArrayList<>();
        assert returnValueHandlers != null;
        customerReturnValueHandlers.add(userHandlerMethodReturnValueHandler);
        customerReturnValueHandlers.addAll(returnValueHandlers);

        requestMappingHandlerAdapter.setReturnValueHandlers(customerReturnValueHandlers);

    }
}
package com.github.lybgeek.resolver;


import com.github.lybgeek.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;

    public UserHandlerMethodArgumentResolver(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
        this.handlerMethodArgumentResolver = handlerMethodArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class) &&
                parameter.getParameterType().isAssignableFrom(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        User user = (User) handlerMethodArgumentResolver.resolveArgument(parameter,mavContainer,webRequest,binderFactory);
        if(StringUtils.isBlank(user.getId())){
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            String id = request.getHeader("id");
            user.setId(id);
        }

        System.out.println(user);
        return user;
    }
}

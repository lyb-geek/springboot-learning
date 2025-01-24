package com.github.lybgeek.chain.test.user.resovle;


import com.github.lybgeek.chain.delegete.CommandDelegete;
import com.github.lybgeek.chain.test.user.dto.UserDTO;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.web.servlet.ServletWebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Component
public class UserHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private CommandDelegete commandDelegete;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        PostMapping mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(returnType.getMethod(), PostMapping.class);
        if(mergedAnnotation == null){
            return UserDTO.class.isAssignableFrom(returnType.getParameterType());
        }

        return Arrays.stream(mergedAnnotation.value()).noneMatch("save"::equals)
                && UserDTO.class.isAssignableFrom(returnType.getParameterType());


    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Context context = new ServletWebContext(servletContext, request, response);
        context.put("user",returnValue);
        boolean isSuccess = commandDelegete.execute(context);
        mavContainer.setRequestHandled(isSuccess);

    }
}

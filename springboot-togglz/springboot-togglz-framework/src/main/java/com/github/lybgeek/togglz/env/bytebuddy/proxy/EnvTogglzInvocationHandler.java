package com.github.lybgeek.togglz.env.bytebuddy.proxy;


import com.github.lybgeek.togglz.env.exception.EnvTogglzException;
import com.github.lybgeek.togglz.env.feature.EnvFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class EnvTogglzInvocationHandler implements InvocationHandler {

    private final String activeEnv;
    private final Object target;
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(StringUtils.isEmpty(activeEnv)){
            return method.invoke(target, args);
        }

        if(EnvFeature.getEnvFeature(activeEnv).isActive()){
            return method.invoke(target, args);
        }

        throw new EnvTogglzException("非法访问", HttpStatus.FORBIDDEN.value());
    }
}

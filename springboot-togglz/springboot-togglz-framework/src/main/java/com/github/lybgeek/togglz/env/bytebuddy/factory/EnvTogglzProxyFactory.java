package com.github.lybgeek.togglz.env.bytebuddy.factory;


import com.github.lybgeek.togglz.env.bytebuddy.proxy.EnvTogglzInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.implementation.attribute.MethodAttributeAppender;

import java.lang.reflect.InvocationTargetException;

import static net.bytebuddy.matcher.ElementMatchers.any;

@Slf4j
public final class EnvTogglzProxyFactory {
    private EnvTogglzProxyFactory(){}

    public static <T> T createProxy(T target,String activeEnv) {

        Class proxy = new ByteBuddy()
                .subclass(target.getClass())
                .method(any())
                .intercept(InvocationHandlerAdapter.of(new EnvTogglzInvocationHandler(activeEnv,target)))
                .attribute(MethodAttributeAppender.ForInstrumentedMethod.INCLUDING_RECEIVER)
                .annotateType(target.getClass().getAnnotations())
                .make()
                .load(EnvTogglzProxyFactory.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        try {
            return (T)proxy.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            log.error("create proxy error",e);
        }

        return null;

    }
}

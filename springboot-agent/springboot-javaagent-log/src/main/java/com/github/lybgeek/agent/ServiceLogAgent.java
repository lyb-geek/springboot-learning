package com.github.lybgeek.agent;

import com.github.lybgeek.agent.helper.ServiceLogHelperFactory;
import com.github.lybgeek.agent.intercept.ServiceLogInterceptor;
import com.github.lybgeek.agent.util.PropertiesUtils;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.lang.instrument.Instrumentation;
import java.util.Properties;

public class ServiceLogAgent {


    public static String base_package_key = "agent.basePackage";

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("loaded agentArgs ：" + agentArgs);
        Properties properties = PropertiesUtils.getProperties(agentArgs);
        ServiceLogHelperFactory serviceLogHelperFactory = new ServiceLogHelperFactory(properties);
        serviceLogHelperFactory.getServiceLogHelper().initTable();

        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
            return builder
                    .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                    .intercept(MethodDelegation.to(new ServiceLogInterceptor(serviceLogHelperFactory))); // 委托
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            private Log log = LogFactory.getLog(AgentBuilder.Listener.class);

            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                log.error(throwable.getMessage(),throwable);
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith(properties.getProperty(base_package_key)))
                .and(ElementMatchers.isAnnotatedWith(Service.class))
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }


}
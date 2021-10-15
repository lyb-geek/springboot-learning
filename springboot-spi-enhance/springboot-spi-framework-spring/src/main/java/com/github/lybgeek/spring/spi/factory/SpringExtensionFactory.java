package com.github.lybgeek.spring.spi.factory;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spi.anotatation.SPI;
import com.github.lybgeek.spi.factory.ExtensionFactory;
import com.github.lybgeek.spi.factory.InterceptorExtensionFactory;
import com.github.lybgeek.spring.spi.util.BeanFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;

import java.util.Set;

@Activate
@Slf4j
public class SpringExtensionFactory implements ExtensionFactory, SmartLifecycle {

    private volatile boolean running = false;

    private static final Set<ApplicationContext> CONTEXTS = new ConcurrentHashSet<>();

    private InterceptorChain chain;

    public static void addApplicationContext(ApplicationContext context) {
        CONTEXTS.add(context);

    }

    public static void removeApplicationContext(ApplicationContext context) {
        CONTEXTS.remove(context);
    }

    public static Set<ApplicationContext> getContexts() {
        return CONTEXTS;
    }

    // currently for test purpose
    public static void clearContexts() {
        CONTEXTS.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getExtension(String key, Class<T> clazz) {

        //SPI should be get from SpiExtensionFactory
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            return null;
        }

        for (ApplicationContext context : CONTEXTS) {
            T bean = BeanFactoryUtils.getOptionalBean(context, key, clazz);
            if (bean != null) {
                if(chain != null){
                    return (T) chain.pluginAll(bean);
                }
                return bean;
            }
        }

        log.warn("No spring extension (bean) named:{}, try to find an extension (bean) of type :{} ",key,clazz.getName() );

        return null;
    }

    public InterceptorChain getChain() {
        return chain;
    }

    public SpringExtensionFactory setChain(InterceptorChain chain) {
        this.chain = chain;
        return this;
    }


    @Override
    public void start() {
         running = true;
    }

    @Override
    public void stop() {
       clearContexts();
       running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }


}

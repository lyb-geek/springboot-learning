package com.github.lybgeek.circuitbreaker.spi.factory;

import com.github.lybgeek.circuitbreaker.spi.proxy.CircuitBreakerProxy;
import com.github.lybgeek.circuitbreaker.spi.proxy.ProxyFactory;
import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spi.anotatation.SPI;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Activate
@Slf4j
public class CircuitBreakerExtensionFactory extends AbstractCircuitBreakerExtensionFactory {

    private InterceptorChain chain;

    public CircuitBreakerExtensionFactory(ProxyFactory proxyFactory) {
        super(proxyFactory);
    }


    @Override
    public <T> T getExtension(final String key, final Class<T> clazz) {
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            if (!extensionLoader.getSupportedExtensions().isEmpty()) {
                return getSpiService(key, extensionLoader);

            }
        }
        return null;
    }

    private <T> T getSpiService(String key, ExtensionLoader<T> extensionLoader) {
        if(ObjectUtils.isNotEmpty(chain)){
            if(StringUtils.isBlank(key)){
                return (T) chain.pluginAll(extensionLoader.getDefaultActivate());
            }
            return (T) chain.pluginAll(extensionLoader.getActivate(key));
        }else{
            if(StringUtils.isBlank(key)){
                return extensionLoader.getDefaultActivate();
            }
            return extensionLoader.getActivate(key);
        }
    }

    public InterceptorChain getChain() {
        return chain;
    }

    public CircuitBreakerExtensionFactory setChain(InterceptorChain chain) {
        this.chain = chain;
        return this;
    }


}
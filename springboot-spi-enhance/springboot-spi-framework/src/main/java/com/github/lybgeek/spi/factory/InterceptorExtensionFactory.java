package com.github.lybgeek.spi.factory;

import com.github.lybgeek.interceptor.model.InterceptorChain;
import com.github.lybgeek.spi.anotatation.Activate;
import com.github.lybgeek.spi.anotatation.SPI;
import com.github.lybgeek.spi.extension.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Activate
@Slf4j
public class InterceptorExtensionFactory implements ExtensionFactory {

    private InterceptorChain chain;


    @Override
    public <T> T getExtension(final String key, final Class<T> clazz) {
        if(Objects.isNull(chain)){
            log.warn("No InterceptorChain Is Config" );
            return null;
        }
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            if (!extensionLoader.getSupportedExtensions().isEmpty()) {
               if(StringUtils.isBlank(key)){
                   return (T) chain.pluginAll(extensionLoader.getDefaultActivate());
               }
               return (T) chain.pluginAll(extensionLoader.getActivate(key));
            }
        }
        return null;
    }

    public InterceptorChain getChain() {
        return chain;
    }

    public InterceptorExtensionFactory setChain(InterceptorChain chain) {
        this.chain = chain;
        return this;
    }


}
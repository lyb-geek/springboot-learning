package com.github.lybgeek.aop.core.plugin;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.aop.core.locator.ProxyMetaDefinitionLocator;
import com.github.lybgeek.aop.core.model.ProxyMetaDefinition;
import com.github.lybgeek.aop.core.model.ProxyMetaInfo;
import com.github.lybgeek.aop.core.util.AopUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static com.github.lybgeek.aop.core.constant.Constant.SPIILT;
import static com.github.lybgeek.aop.core.util.AopUtil.PROXY_PLUGIN_PREFIX;

@RequiredArgsConstructor
public class AopPluginFactory implements SmartInitializingSingleton {

    private final ProxyMetaDefinitionLocator proxyMetaDefinitionLocator;

    private final DefaultListableBeanFactory defaultListableBeanFactory;


    public void installPlugin(ProxyMetaInfo proxyMetaInfo){
        if(StringUtils.isEmpty(proxyMetaInfo.getId())){
            proxyMetaInfo.setId(proxyMetaInfo.getProxyUrl() + SPIILT + proxyMetaInfo.getProxyClassName());
        }
        AopUtil.registerProxy(defaultListableBeanFactory,proxyMetaInfo);

    }


    public void uninstallPlugin(String id){
        String beanName = PROXY_PLUGIN_PREFIX + id;
        if(defaultListableBeanFactory.containsBean(beanName)){
           AopUtil.destoryProxy(defaultListableBeanFactory,id);
        }else{
            throw new NoSuchElementException("Plugin not found: " + id);
        }
    }




    public void initPlugin() {
        List<ProxyMetaDefinition> proxyMetaDefinitions = proxyMetaDefinitionLocator.getProxyMetaDefinitions();
        if(CollectionUtil.isNotEmpty(proxyMetaDefinitions)){
            for (ProxyMetaDefinition proxyMetaDefinition : proxyMetaDefinitions) {
                ProxyMetaInfo proxyMetaInfo = getProxyMetaInfo(proxyMetaDefinition);
                AopUtil.registerProxy(defaultListableBeanFactory,proxyMetaInfo);
            }
        }
    }

    public ProxyMetaInfo getProxyMetaInfo(ProxyMetaDefinition proxyMetaDefinition) {
        validateProxyMetaDefinition(proxyMetaDefinition);
        String id = StringUtils.hasText(proxyMetaDefinition.getId()) ? proxyMetaDefinition.getId() : proxyMetaDefinition.getProxyUrl() + SPIILT + proxyMetaDefinition.getProxyClassName();
        return ProxyMetaInfo.builder()
                .id(id).proxyUrl(proxyMetaDefinition.getProxyUrl())
                .proxyClassName(proxyMetaDefinition.getProxyClassName())
                .pointcut(proxyMetaDefinition.getPointcut())
                .build();
    }

    private void validateProxyMetaDefinition(ProxyMetaDefinition proxyMetaDefinition) {
        if(ObjectUtils.isEmpty(proxyMetaDefinition)){
            throw new IllegalArgumentException("ProxyMetaDefinition can not be empty");
        }
    }


    @Override
    public void afterSingletonsInstantiated() {
        initPlugin();
    }


}

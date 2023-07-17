package com.github.lybgeek.apolloconfig.service.impl;


import cn.hutool.core.util.StrUtil;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.github.lybgeek.apolloconfig.model.OpenItemAndReleaseDTO;
import com.github.lybgeek.apolloconfig.property.ApolloOpenApiProperty;
import com.github.lybgeek.apolloconfig.service.ApolloConfigService;
import com.github.lybgeek.apolloconfig.util.ApolloHelper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class ApolloConfigServiceImpl implements ApolloConfigService, ApplicationContextAware, EnvironmentAware {
    
    private final ApolloOpenApiProperty property;
    
    private ApplicationContext applicationContext;
    
    @Override
    public boolean publishAndListenerItem(OpenItemAndReleaseDTO openItemAndReleaseDTO) {
        OpenItemDTO openItemDTO = openItemAndReleaseDTO.getOpenItemDTO();
        fillOperaterIfNotSet(openItemAndReleaseDTO, openItemDTO);

        return ApolloHelper.publishAndListenerItem(applicationContext,property,openItemAndReleaseDTO);
    }

    private void fillOperaterIfNotSet(OpenItemAndReleaseDTO openItemAndReleaseDTO, OpenItemDTO openItemDTO) {
        if(StrUtil.isBlank(openItemDTO.getDataChangeCreatedBy())){
            openItemDTO.setDataChangeCreatedBy(property.getDataChangeCreatedBy());
        }

        NamespaceReleaseDTO releaseDTO = openItemAndReleaseDTO.getReleaseDTO();
        if(StrUtil.isBlank(releaseDTO.getReleasedBy())){
            releaseDTO.setReleasedBy(property.getReleasedBy());
        }
    }

    @Override
    public void registerListener(String key) {
        ApolloHelper.listenItemChange(applicationContext,property.getNamespaceName(), Sets.newHashSet(key));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        if(StrUtil.isBlank(property.getAppId())){
            property.setAppId(environment.getProperty("app.id"));
        }
    }
}

package com.github.lybgeek.apolloconfig.util;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.github.lybgeek.apolloconfig.event.ApolloConfigChangeEvent;
import com.github.lybgeek.apolloconfig.listener.ApolloConfigKeyChangeListener;
import com.github.lybgeek.apolloconfig.model.OpenItemAndReleaseDTO;
import com.github.lybgeek.apolloconfig.property.ApolloOpenApiProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Set;


@Slf4j
public final class ApolloHelper {
    
    private ApolloHelper(){
        
    }


    public static boolean publishAndListenerItem(ApplicationContext context,ApolloOpenApiProperty property, OpenItemAndReleaseDTO openItemAndReleaseDTO) {
        boolean isPushlisSuccess = publishItem(property, openItemAndReleaseDTO);
        boolean isListenItemChangeSuccess = listenItemChange(context,property.getNamespaceName(),openItemAndReleaseDTO.getInterestedKeys());
        return isPushlisSuccess &&  isListenItemChangeSuccess;
    }



    public static boolean publishItem(ApolloOpenApiProperty property, OpenItemAndReleaseDTO openItemAndReleaseDTO) {

        try {
            String portalUrl = property.getPortalUrl(); // portal url
            String token = property.getToken(); // 申请的token
            ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                    .withPortalUrl(portalUrl)
                    .withToken(token)
                    .build();
            client.createOrUpdateItem(property.getAppId(), property.getEnv(), property.getClusterName(), property.getNamespaceName(), openItemAndReleaseDTO.getOpenItemDTO());
            client.publishNamespace(property.getAppId(), property.getEnv(), property.getClusterName(), property.getNamespaceName(), openItemAndReleaseDTO.getReleaseDTO());
            return true;
        }catch (Exception e){
            log.error("publishItem fail,cause:" + e.getMessage(),e);
        }
        return false;

    }

    public static boolean listenItemChange(ApplicationContext context,String nameSpace,Set<String> interestedKeys) {

        try {
            Config config = ConfigService.getConfig(nameSpace);
            ApolloConfigKeyChangeListener configKeyChangeListener = context.getBean(ApolloConfigKeyChangeListener.class);
            config.addChangeListener(configKeyChangeListener,interestedKeys);
            return true;
        }catch (Exception e){
            log.error("listenItemChange fail,cause:" + e.getMessage(),e);
        }
        return false;

    }


}
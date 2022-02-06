package com.github.lybgeek.gateway.dashboard.route.service;


import com.ctrip.framework.apollo.openapi.client.ApolloOpenApiClient;
import com.ctrip.framework.apollo.openapi.dto.NamespaceReleaseDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenItemDTO;
import com.ctrip.framework.apollo.openapi.dto.OpenNamespaceDTO;
import com.github.lybgeek.apollo.property.AppInfoProperties;
import com.github.lybgeek.gateway.dashboard.route.model.RouteRule;
import com.github.lybgeek.gateway.dashboard.route.util.NumberUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RouteService {

    @Autowired
    private ApolloOpenApiClient apolloOpenApiClient;

    @Autowired
    private AppInfoProperties appInfoProperties;

    private static final String ID_PATTERN = "spring\\.cloud\\.gateway\\.routes\\[\\d+\\]\\.id";

    private static final String ROUTE_ID_KEY = "spring.cloud.gateway.routes[%d].id";
    private static final String ROUTE_URI_KEY = "spring.cloud.gateway.routes[%d].uri";
    private static final String ROUTE_PREDICATES_KEY = "spring.cloud.gateway.routes[%d].predicates[%d]";
    private static final String ROUTE_FILTERS_KEY = "spring.cloud.gateway.routes[%d].filters[%d]";

    public long getMaxRouteRuleIndex(){
        OpenNamespaceDTO openNamespaceDTO = apolloOpenApiClient.getNamespace(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName(),appInfoProperties.getNameSpaceName());
        List<OpenItemDTO> items = openNamespaceDTO.getItems();
        if(CollectionUtils.isEmpty(items)){
            return 0;
        }
        return items.stream().filter(item -> item.getKey().matches(ID_PATTERN)).count();
    }


    public boolean createRouteRule(RouteRule routeRule){
        try {
            long curRouteRuleIndex = getMaxRouteRuleIndex();
            buildOpenItemDTO(ROUTE_ID_KEY,curRouteRuleIndex,routeRule.getRouteId(),true);
            buildOpenItemDTO(ROUTE_URI_KEY,curRouteRuleIndex,routeRule.getUri(),true);
            buildOpenItemDTO(ROUTE_PREDICATES_KEY,curRouteRuleIndex,routeRule.getPredicate(),true);
            buildOpenItemDTO(ROUTE_FILTERS_KEY,curRouteRuleIndex,routeRule.getFilter(),true);
            return publish("新增网关路由","新增网关路由");
        } catch (Exception e) {
           log.error("{}",e.getMessage());
        }
        return false;
    }


    public boolean updateRouteRule(RouteRule routeRule){
        long ruleIndex = getRouteRuleIndex(routeRule.getRouteId());
        if(ruleIndex != -1){
            try {
                buildOpenItemDTO(ROUTE_URI_KEY,ruleIndex,routeRule.getUri(),false);
                buildOpenItemDTO(ROUTE_PREDICATES_KEY,ruleIndex,routeRule.getPredicate(),false);
                buildOpenItemDTO(ROUTE_FILTERS_KEY,ruleIndex,routeRule.getFilter(),false);
                return publish("更新网关路由","更新网关路由");
            } catch (Exception e) {
                log.error("{}",e.getMessage());
            }
        }

        return false;
    }


    public boolean deleteRouteRule(String routeId){
        long ruleIndex = getRouteRuleIndex(routeId);

        if(ruleIndex != -1){
            try {
//                removeRouteItem(ROUTE_URI_KEY,ruleIndex);
//                removeRouteItem(ROUTE_PREDICATES_KEY,ruleIndex);
//                removeRouteItem(ROUTE_FILTERS_KEY,ruleIndex);
                buildOpenItemDTO(ROUTE_URI_KEY,ruleIndex,"http://null",false);
                buildOpenItemDTO(ROUTE_PREDICATES_KEY,ruleIndex,"Path=/-9999",false);
                return publish("删除网关路由","删除网关路由");
            } catch (Exception e) {
                log.error("{}",e.getMessage());
            }
        }

        return false;
    }


    public long getRouteRuleIndex(String routeId){
        List<OpenNamespaceDTO> openNamespaceDTOS = apolloOpenApiClient.getNamespaces(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName());
        for (OpenNamespaceDTO openNamespaceDTO : openNamespaceDTOS) {
            List<OpenItemDTO> items = openNamespaceDTO.getItems();
            if(!CollectionUtils.isEmpty(items)){
                for (OpenItemDTO item : items) {
                    if(item.getValue().equals(routeId)){
                        int index = NumberUtils.extractDigits(item.getKey()).get(0);
                        return index;
                    }
                }
            }
        }

        return -1;

    }


    private void removeRouteItem(String key,long index){
        if(key.equalsIgnoreCase(ROUTE_PREDICATES_KEY) || key.equalsIgnoreCase(ROUTE_FILTERS_KEY)){
            key = String.format(key,index,0);
        }else{
            key = String.format(key,index);
        }
        apolloOpenApiClient.removeItem(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName(),appInfoProperties.getNameSpaceName(),key,appInfoProperties.getAuthUser());
    }


    private void buildOpenItemDTO(String key,long index,String value,boolean isAdd){
        OpenItemDTO openItemDTO = new OpenItemDTO();
        if(key.equalsIgnoreCase(ROUTE_PREDICATES_KEY) || key.equalsIgnoreCase(ROUTE_FILTERS_KEY)){
            openItemDTO.setKey(String.format(key,index,0));
        }else{
            openItemDTO.setKey(String.format(key,index));
        }
        openItemDTO.setValue(value);
        openItemDTO.setDataChangeCreatedBy(appInfoProperties.getAuthUser());
        openItemDTO.setDataChangeLastModifiedTime(new Date());
        openItemDTO.setDataChangeLastModifiedBy(openItemDTO.getDataChangeCreatedBy());
        if(isAdd){
            openItemDTO.setDataChangeCreatedTime(new Date());
            apolloOpenApiClient.createItem(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName(),appInfoProperties.getNameSpaceName(),openItemDTO);
        }
        apolloOpenApiClient.updateItem(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName(),appInfoProperties.getNameSpaceName(),openItemDTO);;

    }


    private boolean publish(String comment,String title){

        try {
            NamespaceReleaseDTO namespaceReleaseDTO = new NamespaceReleaseDTO();
            namespaceReleaseDTO.setReleaseComment(comment);
            namespaceReleaseDTO.setReleasedBy(appInfoProperties.getAuthUser());
            namespaceReleaseDTO.setReleaseTitle(title);
            apolloOpenApiClient.publishNamespace(appInfoProperties.getAppId(),appInfoProperties.getEnv(),appInfoProperties.getClusterName(),appInfoProperties.getNameSpaceName(),namespaceReleaseDTO);
            return true;
        } catch (Exception e) {
            log.error("{}",e);
        }

        return false;

    }


}

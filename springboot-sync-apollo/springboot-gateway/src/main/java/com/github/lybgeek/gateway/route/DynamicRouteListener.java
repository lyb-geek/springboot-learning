package com.github.lybgeek.gateway.route;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @description: 动态路由监听
 **/
@Component
@Profile(value = "apollo")
public class DynamicRouteListener implements ApplicationContextAware, ApplicationEventPublisherAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ID_PATTERN = "spring\\.cloud\\.gateway\\.routes\\[\\d+\\]\\.id";

    private static final String DEFAULT_FILTER_PATTERN = "spring\\.cloud\\.gateway\\.default-filters\\[\\d+\\]\\.name";

    private ApplicationContext applicationContext;

    private ApplicationEventPublisher publisher;

    @Autowired
    private GatewayProperties gatewayProperties;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @ApolloConfigChangeListener(interestedKeyPrefixes = "spring.cloud.gateway.")
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshGatewayProperties(changeEvent);
    }

    /***
     * 刷新org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator中定义的routes
     *
     * @param changeEvent
     * @return void
     *
     */
    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {
        logger.info("Refreshing GatewayProperties!");
        preDestroyGatewayProperties(changeEvent);
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        refreshGatewayRouteDefinition();
        logger.info("GatewayProperties refreshed!");
    }

    /***
     * GatewayProperties没有@PreDestroy和destroy方法
     * org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#rebind(java.lang.String)中destroyBean时不会销毁当前对象
     * 如果把spring.cloud.gateway.前缀的配置项全部删除（例如需要动态删除最后一个路由的场景），initializeBean时也无法创建新的bean，则return当前bean
     * 若仍保留有spring.cloud.gateway.routes[n]或spring.cloud.gateway.default-filters[n]等配置，initializeBean时会注入新的属性替换已有的bean
     * 这个方法提供了类似@PreDestroy的操作，根据配置文件的实际情况把org.springframework.cloud.gateway.config.GatewayProperties#routes
     * 和org.springframework.cloud.gateway.config.GatewayProperties#defaultFilters两个集合清空
     *
     * @param
     * @return void
     *
     */
    private synchronized void preDestroyGatewayProperties(ConfigChangeEvent changeEvent) {
        logger.info("Pre Destroy GatewayProperties!");
        final boolean needClearRoutes = this.checkNeedClear(changeEvent, ID_PATTERN, this.gatewayProperties.getRoutes().size());
        if (needClearRoutes) {
            this.gatewayProperties.setRoutes(new ArrayList<>());
        }
        final boolean needClearDefaultFilters = this.checkNeedClear(changeEvent, DEFAULT_FILTER_PATTERN, this.gatewayProperties.getDefaultFilters().size());
        if (needClearDefaultFilters) {
            this.gatewayProperties.setRoutes(new ArrayList<>());
        }
        logger.info("Pre Destroy GatewayProperties finished!");
    }

    private void refreshGatewayRouteDefinition() {
        logger.info("Refreshing Gateway RouteDefinition!");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        logger.info("Gateway RouteDefinition refreshed!");
    }

    /***
     * 根据changeEvent和定义的pattern匹配key，如果所有对应PropertyChangeType为DELETED则需要清空GatewayProperties里相关集合
     *
     * @param changeEvent
     * @param pattern
     * @param existSize
     * @return boolean
     *
     */
    private boolean checkNeedClear(ConfigChangeEvent changeEvent, String pattern, int existSize) {

        return changeEvent.changedKeys().stream().filter(key -> key.matches(pattern))
                .filter(key -> {
                    ConfigChange change = changeEvent.getChange(key);
                    return PropertyChangeType.DELETED.equals(change.getChangeType());
                }).count() == existSize;
    }

}

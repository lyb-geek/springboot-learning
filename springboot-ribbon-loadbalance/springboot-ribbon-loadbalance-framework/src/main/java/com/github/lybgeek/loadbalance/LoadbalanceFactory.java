package com.github.lybgeek.loadbalance;


import com.github.lybgeek.loadbalance.model.RuleDefinition;
import com.github.lybgeek.loadbalance.property.LoadBalanceProperty;
import com.github.lybgeek.loadbalance.updater.EmptyServerListUpdater;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.configuration.ConfigurationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.lybgeek.loadbalance.constant.LoadBalanceConstant.*;

@RequiredArgsConstructor
public class LoadbalanceFactory {

    private final LoadBalanceProperty loadBalanceProperty;

    // key:serviceName + nameSpace
    private static final Map<String, ILoadBalancer> loadBalancerMap = new ConcurrentHashMap<>();

    public ILoadBalancer getLoadBalancer(String serviceName,String namespace){
        String key = serviceName + RULE_JOIN + namespace;
        if(loadBalancerMap.containsKey(key)){
            return loadBalancerMap.get(key);
        }
        RuleDefinition ruleDefinition = getAvailableRuleDefinition(serviceName,namespace);
        IPing ping = ruleDefinition.getPing();
        if(ObjectUtils.isEmpty(ping)){
            // 无法通过ConfigurationManager.getConfigInstance().setProperty(serviceName + DOT + namespace + DOT + PING_CLASS_NAME, ruleDefinition.getLoadBalancerPingClassName());
            //LoadBalancerBuilder没提供通过ClientConfig配置ping方法，只能通过withPing修改
            ping = getPing(serviceName,namespace);
        }

        IRule rule = ruleDefinition.getRule();
        if(ObjectUtils.isEmpty(rule)){
            // 也可以通过ConfigurationManager.getConfigInstance().setProperty(serviceName + DOT + namespace + DOT + RULE_CLASS_NAME, ruleDefinition.getLoadBalancerRuleClassName());
            rule = getRule(serviceName,namespace);
        }


        // 配置服务列表
        ConfigurationManager.getConfigInstance().setProperty(serviceName + DOT + namespace + DOT + SERVER_LIST, ruleDefinition.getListOfServers());
        // 因为服务列表目前是配置写死，因此关闭列表更新，否则当触发定时更新时，会重新将服务列表状态恢复原样,这样会导致server的isLive状态不准确
        // 不设置默认采用com.netflix.loadbalancer.PollingServerListUpdater
        ConfigurationManager.getConfigInstance().setProperty(serviceName + DOT + namespace + DOT + SERVERLIST_UPDATER_CLASS_NAME, EmptyServerListUpdater.class.getName());
        IClientConfig config = new DefaultClientConfigImpl(namespace);
        config.loadProperties(serviceName);
        ZoneAwareLoadBalancer<Server> loadBalancer = getLoadBalancer(config, ping, rule);
        loadBalancerMap.put(key,loadBalancer);
        if(ruleDefinition.getPingIntervalSeconds() > 0){
            loadBalancer.setPingInterval(ruleDefinition.getPingIntervalSeconds());
        }

        return loadBalancer;


    }



    public ZoneAwareLoadBalancer<Server> getLoadBalancer(IClientConfig config, IPing ping, IRule rule){
        ZoneAwareLoadBalancer<Server> serverZoneAwareLoadBalancer = LoadBalancerBuilder.newBuilder()
                .withClientConfig(config)
                // 默认每隔10秒进行心跳检测
                .withPing(ping)
                .withRule(rule)
                .buildDynamicServerListLoadBalancerWithUpdater();
        return serverZoneAwareLoadBalancer;
    }






    /**
     * 获取 iping
     * @param serviceName
     * @param namespace
     * @return
     */
    @SneakyThrows
    public IPing getPing(String serviceName, String namespace){
        RuleDefinition ruleDefinition = getAvailableRuleDefinition(serviceName,namespace);
        Class<?> loadBalancerPingClass = ClassUtils.forName(ruleDefinition.getLoadBalancerPingClassName(), Thread.currentThread().getContextClassLoader());
        Assert.isTrue(IPing.class.isAssignableFrom(loadBalancerPingClass),String.format("loadBalancerPingClassName : [%s] is not Iping class type",ruleDefinition.getLoadBalancerPingClassName()));
        return (IPing) BeanUtils.instantiateClass(loadBalancerPingClass);


    }

    /**
     * 获取 loadbalanceRule
     * @param serviceName
     * @param namespace
     * @return
     */
    @SneakyThrows
    public IRule getRule(String serviceName, String namespace){
        RuleDefinition ruleDefinition = getAvailableRuleDefinition(serviceName,namespace);
        Class<?> loadBalancerRuleClass = ClassUtils.forName(ruleDefinition.getLoadBalancerRuleClassName(), Thread.currentThread().getContextClassLoader());
        Assert.isTrue(IRule.class.isAssignableFrom(loadBalancerRuleClass),String.format("loadBalancerRuleClassName : [%s] is not Irule class type",ruleDefinition.getLoadBalancerRuleClassName()));
        return (IRule) BeanUtils.instantiateClass(loadBalancerRuleClass);

    }

    private RuleDefinition getAvailableRuleDefinition(String serviceName,String namespace){
        Map<String, RuleDefinition> ruleMap = loadBalanceProperty.getRuleMap();
        Assert.notEmpty(ruleMap,"ruleDefinition is empty");
        String key = serviceName + RULE_JOIN + namespace;
        RuleDefinition ruleDefinition = ruleMap.get(key);
        Assert.notNull(ruleDefinition,String.format("NOT FOUND AvailableRuleDefinition with serviceName : [{}] in namespace:[{}]",serviceName,namespace));
        return ruleDefinition;
    }



}

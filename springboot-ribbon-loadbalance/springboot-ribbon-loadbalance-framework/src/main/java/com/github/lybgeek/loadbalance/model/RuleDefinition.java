package com.github.lybgeek.loadbalance.model;


import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.github.lybgeek.loadbalance.constant.LoadBalanceConstant.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleDefinition {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 命名空间，当服务名相同时，可以通过namesapce来进行隔离区分
     * 未指定默认为public
     */
    @Builder.Default
    private String namespace = DEFAULT_NAMESPACE;

    /**
     * 自定义负载均衡策略，未指定默认为轮询
     */
    @Builder.Default
    private String loadBalancerRuleClassName = RoundRobin;

    /**
     * 自定义心跳检测，未指定不检测
     */
    @Builder.Default
    private String loadBalancerPingClassName = DummyPing;

    /**
     * 服务列表，多个用英文逗号隔开
     */
    private String listOfServers;


    /**
     * 该优先级大于loadBalancerPingClassName
     */
    private IPing ping;

    /**
     * 心跳间隔，不配置默认是10秒，单位秒
     */
    private int pingIntervalSeconds;


    /**
     * 该优先级大于loadBalancerRuleClassName
     */
    private IRule rule;





}

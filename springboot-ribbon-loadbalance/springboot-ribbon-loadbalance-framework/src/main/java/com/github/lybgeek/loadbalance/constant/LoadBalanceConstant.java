package com.github.lybgeek.loadbalance.constant;


public final class LoadBalanceConstant {
    private LoadBalanceConstant(){}

    public static final String RoundRobin = "com.netflix.loadbalancer.RoundRobinRule";

    public static final String DummyPing = "com.netflix.loadbalancer.DummyPing";

    public static final String RULE_JOIN = "_";

    public static final String SERVER_LIST = "listOfServers";

    //具体名字查看 com.netflix.client.config.CommonClientConfigKey
    public static final String PING_CLASS_NAME = "NFLoadBalancerPingClassName";

    public static final String RULE_CLASS_NAME = "NFLoadBalancerRuleClassName";

    public static final String SERVERLIST_UPDATER_CLASS_NAME = "ServerListUpdaterClassName";

    public static final String DOT = ".";

    public static final String DEFAULT_NAMESPACE = "public";

}

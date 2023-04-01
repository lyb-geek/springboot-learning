package com.github.lybgeek.loadbalance;


import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerChooser {

    private ILoadBalancer loadBalancer;

    public Server getServer(String serverName){
        validateLoadBalancer();
        return loadBalancer.chooseServer(serverName);
    }

    public List<Server> getReachableServers(){
        validateLoadBalancer();
        return loadBalancer.getReachableServers();
    }

    private void validateLoadBalancer(){
        Assert.notNull(loadBalancer,"loadBalancer can not be empty");
    }
}

package com.github.lybgeek.registercenter.discovery;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.registercenter.discovery.model.PeerUri;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@RequiredArgsConstructor
public class NamingService {

    private final static String CLIENT_IP_KEY = "ipAddress";
    private final static String SEPARATOR = ":";
    private final DiscoveryClient discoveryClient;

    private AtomicInteger localPort = new AtomicInteger();

    public List<PeerUri> discover(String serviceId,boolean isExludeSelfUri){
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if(CollectionUtil.isEmpty(instances)){
            return Collections.emptyList();
        }

        List<PeerUri> peerUris = new ArrayList<>();
        for (ServiceInstance instance : instances) {
            System.out.println(">>>>>>>>>>>> serviceId:" + instance.getServiceId());
            System.out.println(">>>>>>>>>>>> host:" + instance.getHost());
            System.out.println(">>>>>>>>>>>> port:" + instance.getPort());
            System.out.println(">>>>>>>>>>>> scheme:" + instance.getScheme());
            System.out.println(">>>>>>>>>>>> uri:" + instance.getUri());
            System.out.println(">>>>>>>>>>>> instanceId:" + instance.getInstanceId());
            System.out.println(">>>>>>>>>>>> metaData:" + JSONUtil.toJsonStr(instance.getMetadata()));
           if(isExludeSelfUri){
               if(isLocalUri(instance)){
                   continue;
               }
           }
            PeerUri peerUri = PeerUri.builder()
                    .host(instance.getHost())
                    .port(instance.getPort())
                    .uri(instance.getUri().toString())
                    .ip(getClientIp(instance))
                    .httpUrl(buildHttpUrl(instance))
                    .build();
            peerUris.add(peerUri);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        return Collections.unmodifiableList(peerUris);
    }


    private boolean isLocalUri(ServiceInstance instance){
        String localHost = NetUtil.getLocalhostStr();
        String clientIp = getClientIp(instance);
        return localHost.equals(clientIp) && instance.getPort() == localPort.get();

    }


    private String buildHttpUrl(ServiceInstance instance){
        String host = getClientIp(instance);
        return URLUtil.normalize(host + SEPARATOR + instance.getPort());
    }

    /**
     * 通过元数据获取。客户端需做如下配置
     * eureka:
     *   instance:
     *     metadata-map:
     *       ipAddress: ${spring.cloud.client.ip-address}
     * @param instance
     * @return
     */
    private String getClientIp(ServiceInstance instance){
        Map<String, String> metadata = instance.getMetadata();
        if(metadata.containsKey(CLIENT_IP_KEY)){
            return metadata.get(CLIENT_IP_KEY);
        }

        return instance.getHost();
    }

    @EventListener
    public void addLocalPort(WebServerInitializedEvent event){
        localPort.set(event.getWebServer().getPort());
    }


}

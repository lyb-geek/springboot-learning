package com.github.lybgeek.loadbalance.ping;


import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

@Slf4j
public class TelnetPing implements IPing {

    public String TELNET_PING_TIME_OUT = "telnet.ping.time.out";

    public String DEFAULT_TELNET_PING_TIME_OUT = "2000";

    @Override
    public boolean isAlive(Server server) {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start(server.getId() + "health check");
            SocketAddress address = new InetSocketAddress(server.getHost(), server.getPort());
            @Cleanup Socket socketClient = new Socket();
            socketClient.connect(address, Integer.valueOf(System.getProperty(TELNET_PING_TIME_OUT,DEFAULT_TELNET_PING_TIME_OUT)));
            stopWatch.stop();
            if(log.isDebugEnabled()){
                log.debug(stopWatch.prettyPrint());
            }

        } catch (Exception e) {
            log.error("server [{}] connect fail:{}",server.getId(),e.getMessage());
            return false;
        }

        return true;
    }
}
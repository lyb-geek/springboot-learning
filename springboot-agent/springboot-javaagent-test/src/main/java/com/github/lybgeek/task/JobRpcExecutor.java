package com.github.lybgeek.task;

import com.github.lybgeek.mock.dto.RpcRequest;
import com.github.lybgeek.mock.dto.RpcResponse;
import com.github.lybgeek.mock.service.client.RpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@Component
public class JobRpcExecutor {

    @Autowired
    private RpcClientService rpcClientService;

    private AtomicLong requestIdAtomic = new AtomicLong(1);

    private AtomicLong userIdAtomic = new AtomicLong(1);

    //@Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(fixedDelay = 6000)
    public void run(){
        Long requestId = requestIdAtomic.getAndIncrement();
        Long userId = userIdAtomic.getAndIncrement();
        System.out.println(String.format("requestId:%s,userId:%s",requestId,userId));
        RpcRequest rpcRequest = RpcRequest.builder().requestId(requestId).userId(userId).build();
        RpcResponse rpcResponse = rpcClientService.fetchRemoteUser(rpcRequest);
        System.out.println(rpcResponse);
    }

}

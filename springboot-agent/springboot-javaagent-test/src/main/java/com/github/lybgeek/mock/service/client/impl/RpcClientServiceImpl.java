package com.github.lybgeek.mock.service.client.impl;


import com.github.lybgeek.exception.BizException;
import com.github.lybgeek.mock.dto.RpcRequest;
import com.github.lybgeek.mock.dto.RpcResponse;
import com.github.lybgeek.mock.enity.User;
import com.github.lybgeek.mock.service.client.RpcClientService;
import com.github.lybgeek.mock.service.constant.HttpStatus;
import com.github.lybgeek.mock.service.server.RpcServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcClientServiceImpl implements RpcClientService {

    @Autowired
    private RpcServerService rpcServerService;
    @Override
    public RpcResponse<User> fetchRemoteUser(RpcRequest rpcRequest) {

        RpcResponse<User> rpcResponse = rpcServerService.getUser(rpcRequest.getRequestId(),rpcRequest.getUserId());

        if(rpcResponse.getCode() != HttpStatus.SUCCESS){
            throw new BizException(rpcResponse.getCode(),rpcResponse.getMsg());
        }

        return rpcResponse;
    }

    @Override
    public RpcResponse send(String msg) {
        return RpcResponse.builder().code(200).msg(msg).build();
    }
}
;
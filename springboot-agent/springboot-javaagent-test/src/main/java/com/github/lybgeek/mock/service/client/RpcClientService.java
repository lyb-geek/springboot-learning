package com.github.lybgeek.mock.service.client;


import com.github.lybgeek.mock.dto.RpcRequest;
import com.github.lybgeek.mock.dto.RpcResponse;
import com.github.lybgeek.mock.enity.User;

public interface RpcClientService {

    RpcResponse<User> fetchRemoteUser(RpcRequest rpcRequest);

    RpcResponse send (String msg);
}

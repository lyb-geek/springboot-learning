package com.github.lybgeek.mock.service.server;


import com.github.lybgeek.mock.dto.RpcResponse;
import com.github.lybgeek.mock.enity.User;

public interface RpcServerService {

  RpcResponse<User> getUser(Long requestId, Long userId);
}

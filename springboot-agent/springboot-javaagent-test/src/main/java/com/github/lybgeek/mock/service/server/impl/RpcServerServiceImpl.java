package com.github.lybgeek.mock.service.server.impl;


import com.github.lybgeek.mock.dto.RpcResponse;
import com.github.lybgeek.mock.enity.User;
import com.github.lybgeek.mock.service.constant.HttpStatus;
import com.github.lybgeek.mock.service.server.RpcServerService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RpcServerServiceImpl implements RpcServerService {

    private Map<Long, User> mockUserDb = new ConcurrentHashMap<>();

    @Override
    public RpcResponse getUser(Long requestId, Long userId) {
        RpcResponse<User> rpcResponse = validate(requestId,userId);
        if(rpcResponse.getCode() != HttpStatus.SUCCESS){
            return rpcResponse;
        }

        if(requestId % 2 == 0){
            //模拟长耗时
            try {
                Random random = new Random();
                int index = random.nextInt(10);
                long time = index * 1000;
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        User user = mockUserDb.get(userId);
        rpcResponse.setData(user);

        return rpcResponse;
    }


    private RpcResponse validate(Long requestId, Long userId){
        RpcResponse<User> rpcResponse = new RpcResponse<>();
        Random random = new Random();
        long randomRequestId = random.nextInt(10);
        if (Objects.isNull(requestId)) {
            rpcResponse.setCode(HttpStatus.BAD_REQUEST);
            rpcResponse.setMsg("requestId不能为空");
            //模拟非法requestId
        }else if(randomRequestId == requestId){
            System.out.println("randomRequestId:" + randomRequestId);
            rpcResponse.setCode(HttpStatus.BAD_REQUEST);
            rpcResponse.setMsg("非法的requestId");
        }else if(Objects.isNull(userId)){
            rpcResponse.setCode(HttpStatus.BAD_REQUEST);
            rpcResponse.setMsg("userId不能为空");
        }else if(!mockUserDb.containsKey(userId)){
            rpcResponse.setCode(HttpStatus.NOT_FOUND);
            rpcResponse.setMsg("找不到用户ID为："+userId+"的用户");
        }else{
            rpcResponse.setCode(HttpStatus.SUCCESS);
            rpcResponse.setMsg("操作成功");
        }


        return rpcResponse;
    }


    @PostConstruct
    public void initMockUsers() {
        for (int i = 1; i <= 20; i++) {
            String mobilePrefix = "136000000";
            if(i < 10){
                mobilePrefix = "1360000000";
            }
            User user = User.builder().id(Long.valueOf(i))
                    .userName("user_" + UUID.randomUUID().toString())
                    .age(i + 20).mobile(mobilePrefix + i).build();
            mockUserDb.put(Long.valueOf(i), user);
        }
    }
}
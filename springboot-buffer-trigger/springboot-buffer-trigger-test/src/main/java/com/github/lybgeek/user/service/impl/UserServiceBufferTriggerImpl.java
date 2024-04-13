package com.github.lybgeek.user.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.buffertrigger.builder.BatchConsumerTriggerFactory;
import com.github.lybgeek.buffertrigger.handler.BatchConsumerTriggerHandler;
import com.github.lybgeek.buffertrigger.model.DataExchange;
import com.github.lybgeek.buffertrigger.model.Result;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import com.github.phantomthief.util.ThrowableConsumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.LongAdder;

@Service
@RequiredArgsConstructor
public class UserServiceBufferTriggerImpl implements UserService, InitializingBean, DisposableBean {
    public static final String BUFFER_TRIGGER_BIZ_TYPE = "userReisgeter";

    private final UserDao userDao;

    private final BatchConsumerTriggerFactory batchConsumerTriggerFactory;

    private BatchConsumerTriggerHandler<UserDTO,User> batchConsumerTriggerHandler;


    private final LongAdder count = new LongAdder();

    @SneakyThrows
    @Override
    public Result<User> register(UserDTO user) {
       return batchConsumerTriggerHandler.handle(user,BUFFER_TRIGGER_BIZ_TYPE + "-" + UUID.randomUUID());
    }

    @Override
    public Result<List<User>> findAll() {
        return Result.success(userDao.findAll());
    }

    @Override
    public Result<User> findById(Long id) {
        return Result.success(userDao.findById(id));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // key为业务属性唯一键，如果不存在业务属性唯一键，则可以取bizNo作为key，示例以username作为唯一键
        Map<String, CompletableFuture<Result<User>>> completableFutureMap = new HashMap<>();
        batchConsumerTriggerHandler = batchConsumerTriggerFactory.getTriggerHandler((ThrowableConsumer<List<DataExchange<UserDTO, User>>, Exception>) dataExchanges -> {
            List<UserDTO> userDTOs = new ArrayList<>();
            for (DataExchange<UserDTO, User> dataExchange : dataExchanges) {
                UserDTO userDTO = dataExchange.getRequest();
                completableFutureMap.put(userDTO.getUsername(),dataExchange.getResponse());
                userDTOs.add(userDTO);
            }
            count.increment();
            System.out.println("执行次数：" + count.sum());
            List<User> users = userDao.batchRegister(userDTOs);
            if(CollectionUtil.isNotEmpty(users)){
                for (User user : users) {
                    CompletableFuture<Result<User>> completableFuture = completableFutureMap.remove(user.getUsername());
                    if(completableFuture != null){
                        completableFuture.complete(Result.success(user));
                    }
                }
            }

        },BUFFER_TRIGGER_BIZ_TYPE);


    }

    @Override
    public void destroy() throws Exception {
        // 触发该事件，关闭BufferTrigger，并将未消费的数据消费
        batchConsumerTriggerHandler.closeBufferTrigger();
    }
}

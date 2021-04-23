package com.github.lybgeek.user.service.impl;

import com.github.lybgeek.remote.RpcMockService;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private RpcMockService rpcMockService;

    @Override
    public void saveAndMockLongTimeNotity(User user) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("saveAndMockLongTimeNotity");
        baseMapper.insert(user);
        rpcMockService.mockLongTimeNotity(user);
        stopWatch.stop();
        System.out.println("costTimes:" + stopWatch.prettyPrint());
    }

    @Override
    public void saveAndMockExceptionCall(User user) {
        baseMapper.insert(user);
        rpcMockService.mockExceptionCall(user);
    }

    @Override
    public void saveAndMockNotityWithEvent(User user) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("saveAndMockNotityWithEvent");
        baseMapper.insert(user);
        applicationEventPublisher.publishEvent(user);
        stopWatch.stop();
        System.out.println("costTimes:" + stopWatch.prettyPrint());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
         this.applicationEventPublisher = applicationEventPublisher;
    }
}

package com.github.lybgeek.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.lybgeek.mq.service.MqService;
import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private MqService mqService;

    @Value(Constant.USER_TOPIC)
    private String topic;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndPush(User user) {
        mqService.send(user,topic);
    }

    @Override
    public boolean isExistUserByUsername(String username) {
        int count = baseMapper.selectCount(new QueryWrapper<>(new User()).eq("username", username));
        return count > 0;
    }
}

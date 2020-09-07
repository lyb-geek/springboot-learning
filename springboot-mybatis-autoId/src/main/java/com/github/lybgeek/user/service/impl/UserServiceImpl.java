package com.github.lybgeek.user.service.impl;

import com.github.lybgeek.user.dao.UserMapper;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean save(User record) {
        int count = userMapper.insert(record);
        return count > 0;
    }

    @Override
    public boolean saveBatch(List<User> records) {
        return userMapper.insertBatch(records) > 0;
    }
}

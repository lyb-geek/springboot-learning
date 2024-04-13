package com.github.lybgeek.user.service.impl;


import com.github.lybgeek.buffertrigger.model.Result;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final LongAdder count = new LongAdder();
    @Override
    public Result<User> register(UserDTO user) {
        count.increment();
        System.out.println("执行次数：" + count.sum());

        return Result.success(userDao.register(user));
    }

    @Override
    public Result<List<User>> findAll() {
        return Result.success(userDao.findAll());
    }

    @Override
    public Result<User> findById(Long id) {
        return Result.success(userDao.findById(id));
    }
}

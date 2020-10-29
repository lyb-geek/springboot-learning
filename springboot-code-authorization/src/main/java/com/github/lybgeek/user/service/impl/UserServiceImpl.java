package com.github.lybgeek.user.service.impl;

import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> list() {
       return userDao.list();
    }

    @Override
    public User getUserById(Long id) {
       return userDao.getUserById(id);
    }

    @Override
    public User save(User user) {
       return userDao.save(user);
    }



    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}

package com.github.lybgeek.pipeline.spring.test.anotataion.service.impl;


import com.github.lybgeek.pipeline.spring.test.anotataion.service.UserService;
import com.github.lybgeek.pipeline.spring.test.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public boolean save(User user) {
        System.out.println("UserServiceImpl:" + user);
        return false;
    }
}

package com.github.lybgeek.user.service.impl;

import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

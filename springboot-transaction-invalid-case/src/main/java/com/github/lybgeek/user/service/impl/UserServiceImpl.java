package com.github.lybgeek.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.lybgeek.user.dao.UserDao;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {


}

package com.github.lybgeek.test.user.service.impl;

import com.github.lybgeek.test.user.entity.User;
import com.github.lybgeek.test.user.dao.UserDao;
import com.github.lybgeek.test.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lybgeek
 * @since 2021-12-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

package com.github.lybgeek.user.service;

import com.github.lybgeek.user.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyb-geek
 * @since 2020-10-24
 */
public interface UserService  {

    List<User> list();

    User getUserById(Long id);

    User save(User user);

    boolean delete(Long id);

}

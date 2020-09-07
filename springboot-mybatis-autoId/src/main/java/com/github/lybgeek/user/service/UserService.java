package com.github.lybgeek.user.service;

import com.github.lybgeek.user.entity.User;

import java.util.List;

public interface UserService {

    boolean save(User record);

    boolean saveBatch(List<User> records);
}

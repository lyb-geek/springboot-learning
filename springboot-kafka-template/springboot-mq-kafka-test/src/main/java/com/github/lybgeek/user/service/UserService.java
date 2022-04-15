package com.github.lybgeek.user.service;

import com.github.lybgeek.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

    void saveAndPush(User user);

    public boolean isExistUserByUsername(String username);





}

package com.github.lybgeek.user.service;

import com.github.lybgeek.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

    void saveAndMockLongTimeNotity(User user);

    void saveAndMockExceptionCall(User user);

    /**
     * 基于事件驱动
     * @param user
     */
    void saveAndMockNotityWithEvent(User user);



}

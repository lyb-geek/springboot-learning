package com.github.lybgeek.el.test.service;


import com.github.lybgeek.el.test.annotation.Vip;
import com.github.lybgeek.el.test.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements InitializingBean {

    private final List<User> userList = new ArrayList<>();


    @Vip(expression = "#user.userType == 'vip' || #user.id > 3")
    public List<User> getUserList(User user) {
        if("vip".equals(user.getUserType())){
            return userList;
        }

        return userList.stream().filter(u -> u.getId() <= 3).collect(Collectors.toList());

    }


    @Override
    public void afterPropertiesSet() throws Exception {

        User user = User.builder()
                .userType("vip")
                .name("lybgeek")
                .age(18)
                .address("beijing")
                .email("lybgeek@qq.com")
                .id(1L)
                .build();

        User user1 = User.builder()
                .userType("common")
                .name("zhangsan")
                .age(18)
                .address("beijing")
                .email("zhangsan@qq.com")
                .id(2L)
                .build();

        User user2 = User.builder()
                .userType("common")
                .name("lisi")
                .age(20)
                .address("beijing")
                .email("lisi@qq.com")
                .id(3L)
                .build();

        User user3 = User.builder()
                .userType("common")
                .name("wangwu")
                .age(20)
                .address("beijing")
                .email("wangwu@qq.com")
                .id(4L)
                .build();

        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

    }
}

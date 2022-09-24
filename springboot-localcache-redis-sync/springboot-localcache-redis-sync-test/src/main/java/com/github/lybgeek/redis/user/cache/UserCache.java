package com.github.lybgeek.redis.user.cache;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.lybgeek.redis.LettuceRedisTemplate;
import com.github.lybgeek.redis.user.entity.User;
import com.github.lybgeek.redis.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserCache implements InitializingBean {
    public static final String USER_LIST = "user:list";

    private final LettuceRedisTemplate lettuceRedisTemplate;

    private final UserService userService;


    @Override
    public void afterPropertiesSet() throws Exception {
        initCacheFromDB();
    }

    private void initCacheFromDB(){
        List<User> userList = userService.list();
        if(!CollectionUtils.isEmpty(userList)){
            System.out.println("从数据库加载用户信息到缓存。。。");
            lettuceRedisTemplate.set(USER_LIST,JSON.toJSONString(userList));
            for (User user : userList) {
                String userJson = JSON.toJSONString(user);
                lettuceRedisTemplate.set(user.getUsername(),userJson);
            }
        }
    }

    public List<User> getUsersFromCache(){
        String userListJson = lettuceRedisTemplate.getClientCacheValue(USER_LIST);
        if(JSONUtil.isJson(userListJson)){
            return JSON.parseArray(userListJson,User.class);
        }
        return Collections.emptyList();
    }
}

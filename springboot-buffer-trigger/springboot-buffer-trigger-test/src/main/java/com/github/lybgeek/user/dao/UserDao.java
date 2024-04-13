package com.github.lybgeek.user.dao;


import cn.hutool.core.thread.ThreadUtil;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.util.UserUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

@Repository
public class UserDao {
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final LongAdder idAdder = new LongAdder();

    public User register(UserDTO userDTO){
        mockExecuteCostTime();
        return getUser(userDTO);
    }


    public List<User> batchRegister(List<UserDTO> userDTOs){
        mockExecuteCostTime();
        List<User> users = new ArrayList<>();
        userDTOs.forEach(userDTO -> users.add(getUser(userDTO)));
        return users;
    }

    private User getUser(UserDTO userDTO) {
        idAdder.increment();
        User user = UserUtil.generateUser(userDTO);
        user.setId(idAdder.sum());
        userMap.put(user.getId(), user);
        return user;
    }

    private void mockExecuteCostTime() {
        long mockExecuteTime = random.nextLong(100, 500);
        ThreadUtil.sleep(mockExecuteTime);
    }
    
    public List<User> findAll(){
        return new ArrayList<>(userMap.values());
    }
    
    public User findById(Long id){
        return userMap.get(id);
    }
}

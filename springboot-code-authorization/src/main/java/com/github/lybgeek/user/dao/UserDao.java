package com.github.lybgeek.user.dao;

import com.github.lybgeek.user.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyb-geek
 * @since 2020-10-24
 */
@Repository
public class UserDao  {

    private static Map<Long, User> mockUserTable = new ConcurrentHashMap<>();

    private static LongAdder idGen = new LongAdder();

    public List<User> list() {
        Collection<User> users = mockUserTable.values();
        if(!CollectionUtils.isEmpty(users)){
            return users.stream().collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public User getUserById(Long id) {
        User user = mockUserTable.get(id);
        Assert.notNull(user,"user is not found by id :"+id);
        return user;
    }

    public User save(User user) {
        if(Objects.nonNull(user.getId())){
            return updateUser(user);

        }

        addUser(user);
        return user;
    }

    private void addUser(User user) {
        idGen.increment();
        user.setId(idGen.longValue());
        mockUserTable.put(user.getId(),user);
    }

    private User updateUser(User userParam) {
        User user = mockUserTable.get(userParam.getId());
        Assert.notNull(user,"update fail, cause user is not found by id :"+user.getId());
        if(!StringUtils.isEmpty(userParam.getEmail())){
            user.setEmail(userParam.getEmail());
        }

        if(!StringUtils.isEmpty(userParam.getGender())){
            user.setGender(userParam.getGender());
        }

        if(!StringUtils.isEmpty(userParam.getPassword())){
            user.setPassword(userParam.getPassword());
        }

        if(!StringUtils.isEmpty(userParam.getRealName())){
            user.setRealName(userParam.getRealName());
        }

        if(!StringUtils.isEmpty(userParam.getUserName())){
            user.setUserName(userParam.getUserName());
        }


        return user;
    }

    public boolean delete(Long id) {
        User user = mockUserTable.get(id);
        Assert.notNull(user,"delete fail,cause user is not found by id :"+id);
        mockUserTable.remove(id);
        return true;
    }

}

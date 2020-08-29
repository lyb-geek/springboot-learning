package com.github.lybgeek.user.service.impl;

import com.github.lybgeek.user.convert.UserConvert;
import com.github.lybgeek.user.dto.UserDTO;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserConvert userConvert;

    private static Map<Long, User> mockUserTable = new ConcurrentHashMap<>();

    private static LongAdder idGen = new LongAdder();

    @Override
    public UserDTO getUserById(Long id) {
        User user = mockUserTable.get(id);
        log.info("getUserById-->id:{}",id);
        Assert.notNull(user,"user is not found by id :"+id);
        return userConvert.convertDO2DTO(user);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getId())){
            log.info("updateUser-->id:{}",userDTO.getId());
            return updateUser(userDTO);

        }

        addUser(userDTO);
        log.info("addUser-->{}",userDTO);
        return userDTO;
    }

    private void addUser(UserDTO userDTO) {
        idGen.increment();
        userDTO.setId(idGen.longValue());
        User newUser = userConvert.convertDTO2DO(userDTO);
        mockUserTable.put(newUser.getId(),newUser);
    }

    private UserDTO updateUser(UserDTO userDTO) {
        User user = mockUserTable.get(userDTO.getId());
        Assert.notNull(user,"update fail, cause user is not found by id :"+userDTO.getId());
        if(!StringUtils.isEmpty(userDTO.getEmail())){
            user.setEmail(userDTO.getEmail());
        }

        if(!StringUtils.isEmpty(userDTO.getGender())){
            user.setGender(userDTO.getGender());
        }

        if(!StringUtils.isEmpty(userDTO.getPassword())){
            user.setPassword(userDTO.getPassword());
        }

        if(!StringUtils.isEmpty(userDTO.getRealName())){
            user.setRealName(userDTO.getRealName());
        }

        if(!StringUtils.isEmpty(userDTO.getUserName())){
            user.setUserName(userDTO.getUserName());
        }


        return userConvert.convertDO2DTO(user);
    }

    @Override
    public boolean delete(Long id) {
        User user = mockUserTable.get(id);
        log.info("deleteUserById-->id:{}",id);
        Assert.notNull(user,"delete fail,cause user is not found by id :"+id);
        mockUserTable.remove(id);
        return true;
    }
}

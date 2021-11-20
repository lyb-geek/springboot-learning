package com.github.lybgeek.mybatismate.user.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.lybgeek.mybatismate.user.dao.UserDao;
import com.github.lybgeek.mybatismate.user.dto.UserDTO;
import com.github.lybgeek.mybatismate.user.entity.User;
import com.github.lybgeek.mybatismate.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public List<UserDTO> listUserDTO() {
        List<User> users = this.list();
        if(!CollectionUtils.isEmpty(users)){
         return users.stream().map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setRemark("英雄所见略同，这就是一个大英雄");
                BeanUtils.copyProperties(user,userDTO);
                return userDTO;
            }).collect(Collectors.toList());
        }
        return null;
    }
}

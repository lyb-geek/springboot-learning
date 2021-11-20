package com.github.lybgeek.mybatismate.user.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.github.lybgeek.mybatismate.user.dto.UserDTO;
import com.github.lybgeek.mybatismate.user.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<UserDTO> listUserDTO();

}

package com.github.lybgeek.test.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.lybgeek.test.user.dto.UserDTO;
import com.github.lybgeek.test.user.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    List<UserDTO> listUserDTO();

}

package com.github.lybgeek.user.service;

import com.github.lybgeek.user.dto.UserDTO;

public interface UserService {

    UserDTO getUserById(Long id);

    UserDTO save(UserDTO userDTO);

    boolean delete(Long id);
}

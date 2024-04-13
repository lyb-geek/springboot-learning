package com.github.lybgeek.user.service;


import com.github.lybgeek.buffertrigger.model.Result;
import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;

import java.util.List;

public interface UserService {

    Result<User> register(UserDTO user);

    Result<List<User>> findAll();

    Result<User> findById(Long id);
}

package com.github.lybgeek.mongodb.service;

import com.github.lybgeek.mongodb.common.page.PageQuery;
import com.github.lybgeek.mongodb.common.page.PageResult;
import com.github.lybgeek.mongodb.dto.UserDTO;
import com.github.lybgeek.mongodb.model.User;
import java.util.List;

public interface UserService {

  UserDTO saveUser(UserDTO userDTO);

  boolean delUserById(Long id);

  List<User> listUsers(UserDTO userDTO);

  PageResult<User> pageUsers(PageQuery<UserDTO> pageQuery);

}

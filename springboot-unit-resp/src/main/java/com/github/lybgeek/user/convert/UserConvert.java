package com.github.lybgeek.user.convert;

import com.github.lybgeek.user.dto.UserDTO;
import com.github.lybgeek.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConvert {

    UserDTO convertDO2DTO(User user);

    User convertDTO2DO(UserDTO userDTO);
}

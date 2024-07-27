package com.github.lybgeek.user.convert;

import com.github.lybgeek.user.model.User;
import com.github.lybgeek.user.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConvert {

    User convertDtoToEntity(UserDTO userDTO);

    UserDTO convertEntityToDto(User user);
}

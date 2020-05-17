package com.github.lybgeek.apollo.convert;

import com.github.lybgeek.apollo.model.User;
import com.github.lybgeek.apollo.vo.UserVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserVO convertDO2VO(User user);
}

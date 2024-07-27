package com.github.lybgeek.user.service;


import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.validate.constraint.service.UniqueCheckService;
import com.github.lybgeek.validate.group.CrudValidate;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/***
 * 同时定义了接口和实现类, @Valid加在service接口上, 不是实现类上
 */
@Validated
public interface UserService extends UniqueCheckService {


    UserDTO save(@Valid UserDTO userDTO);

    @Validated(CrudValidate.Update.class)
    UserDTO update(@Valid UserDTO userDTO);

}

package com.github.lybgeek.user.controller;


import com.github.lybgeek.user.model.UserDTO;
import com.github.lybgeek.user.service.UserService;
import com.github.lybgeek.validate.group.CrudValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final HttpServletRequest request;


    @PostMapping("save")
    public UserDTO save(@Validated @RequestBody UserDTO userDTO){
        UserDTO newUser = userService.save(userDTO);
        System.out.println("newUser:" + newUser);
        return newUser;

    }

    /**
     * 通过userService校验
     * @param userDTO
     * @return
     */
    @PostMapping("add")
    public UserDTO add(@RequestBody UserDTO userDTO){
        UserDTO newUser = userService.save(userDTO);
        System.out.println("user:" + newUser);
        return newUser;

    }


    @PostMapping("update")
    public UserDTO update(@Validated(CrudValidate.Update.class) @RequestBody UserDTO userDTO){
        UserDTO updateUser = userService.update(userDTO);
        System.out.println("updateUser:" + updateUser);
        return updateUser;
    }


}

package com.github.lybgeek.chain.test.user.controller;


import cn.hutool.core.util.RandomUtil;
import com.github.lybgeek.chain.test.user.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {


    @PostMapping("save")
    public UserDTO saveUser(UserDTO userDTO){
        userDTO.setId(RandomUtil.randomLong());
        return userDTO;
    }

    @GetMapping("{id}")
    public UserDTO getUser(@PathVariable("id") Long id){

        return UserDTO.builder().id(id).username("张三").mobile("13800000000").build();
    }
}

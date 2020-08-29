package com.github.lybgeek.user.controller;

import com.github.lybgeek.common.validate.group.Add;
import com.github.lybgeek.common.validate.group.Delete;
import com.github.lybgeek.common.validate.group.Update;
import com.github.lybgeek.user.dto.UserDTO;
import com.github.lybgeek.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户管理")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/get/{id}")
    @ApiOperation("根据用户ID查找用户")
    @ApiImplicitParam(value = "用户id",name = "id",required = true,paramType = "path")
    public UserDTO getUserById(@PathVariable("id") Long id){
        UserDTO dto = userService.getUserById(id);
        log.info("{}",dto);
        return dto;

    }

    @PostMapping(value="/add")
    @ApiOperation("添加用户")
    public UserDTO add(@RequestBody @Validated({Add.class}) UserDTO userDTO){
        log.info("{}",userDTO);
        return userService.save(userDTO);
    }

    @PostMapping(value="/update")
    @ApiOperation("更新用户")
    public UserDTO update(@RequestBody @Validated({Update.class}) UserDTO userDTO){
        log.info("{}",userDTO);
        return userService.save(userDTO);
    }

    @DeleteMapping(value="/detele")
    @ApiOperation("删除用户")
    public boolean delete(@Validated({Delete.class}) UserDTO userDTO){
        log.info("id：{}",userDTO.getId());
        return userService.delete(userDTO.getId());
    }
}

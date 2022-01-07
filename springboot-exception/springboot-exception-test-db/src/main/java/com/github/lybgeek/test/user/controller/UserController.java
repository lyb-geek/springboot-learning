package com.github.lybgeek.test.user.controller;


import com.github.lybgeek.test.user.entity.User;
import com.github.lybgeek.test.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lybgeek
 * @since 2021-12-13
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查找用户")
    @ApiImplicitParam(name="id",defaultValue = "1",value="id", paramType = "path")
    public User getById(@PathVariable("id") Long id){
        return userService.getById(id);
    }


    @PostMapping
    @ApiOperation(value = "保存用户")
    public boolean save(@Validated @RequestBody User user){
        return userService.save(user);
    }




}

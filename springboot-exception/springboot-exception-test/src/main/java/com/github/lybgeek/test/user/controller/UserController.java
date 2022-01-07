package com.github.lybgeek.test.user.controller;


import com.github.lybgeek.test.user.client.UserClient;
import com.github.lybgeek.test.user.entity.User;
import com.github.lybgeek.test.util.RestExecutors;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClient userClient;


    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查找用户")
    @ApiImplicitParam(name="id",defaultValue = "1",value="id", paramType = "path")
    public User getById(@PathVariable("id") Long id){
        return RestExecutors.sumbit(() -> userClient.getById(1L));
    }


    @PostMapping
    @ApiOperation(value = "保存用户")
    public boolean save(@Validated @RequestBody User user){
        return RestExecutors.sumbit(()-> userClient.save(user));
    }

}

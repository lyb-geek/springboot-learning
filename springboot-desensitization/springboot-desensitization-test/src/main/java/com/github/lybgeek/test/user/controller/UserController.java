package com.github.lybgeek.test.user.controller;



import com.github.lybgeek.test.model.AjaxResult;
import com.github.lybgeek.test.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value="/list")
    public AjaxResult listUsers(){
       return AjaxResult.success(userService.listUserDTO());
    }




}

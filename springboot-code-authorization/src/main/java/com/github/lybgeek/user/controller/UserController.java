package com.github.lybgeek.user.controller;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyb-geek
 * @since 2020-10-24
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;



    @PostMapping(value = "/save")
    public User save(User user){
		User newUser = userService.save(user);
		return newUser;
    }


    @GetMapping(value="/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping(value="/list")
    public List<User> list(){
        return userService.list();
    }

    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }

}

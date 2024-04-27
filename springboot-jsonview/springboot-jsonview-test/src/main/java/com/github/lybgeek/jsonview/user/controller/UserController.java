package com.github.lybgeek.jsonview.user.controller;


import com.fasterxml.jackson.annotation.JsonView;

import com.github.lybgeek.jsonview.support.PublicJsonView;
import com.github.lybgeek.jsonview.user.model.User;
import com.github.lybgeek.jsonview.user.roleview.AdminJsonView;
import com.github.lybgeek.jsonview.user.roleview.UserJsonView;
import com.github.lybgeek.jsonview.user.roleview.factory.UserJsonViewFactory;
import com.github.lybgeek.jsonview.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserJsonViewFactory userJsonViewFactory;


    @GetMapping("public")
    @JsonView(PublicJsonView.class)
    public User publicUser(){
        return UserUtil.generateUser();
    }

    @GetMapping("default")
    @JsonView(UserJsonView.class)
    public User defaultUser(){
        return UserUtil.generateUser();
    }

    @GetMapping("admin")
    @JsonView(AdminJsonView.class)
    public User adminUser(){
        return UserUtil.generateUser();
    }


    @GetMapping("getUserByToken")
    public MappingJacksonValue getUserByToken(HttpServletRequest request){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(UserUtil.generateUser());
        Class<? extends PublicJsonView> roleViewByToken = userJsonViewFactory.getJsonViewClass(request);
        if(roleViewByToken != null){
            mappingJacksonValue.setSerializationView(roleViewByToken);
        }

        return mappingJacksonValue;

    }

    @GetMapping("/get")
    public User getUser(){
        return UserUtil.generateUser();
    }
}

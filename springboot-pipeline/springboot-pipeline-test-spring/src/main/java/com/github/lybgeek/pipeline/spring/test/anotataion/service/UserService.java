package com.github.lybgeek.pipeline.spring.test.anotataion.service;


import com.github.lybgeek.pipeline.spring.test.model.User;

@FunctionalInterface
public interface UserService {

    boolean save(User user);

}

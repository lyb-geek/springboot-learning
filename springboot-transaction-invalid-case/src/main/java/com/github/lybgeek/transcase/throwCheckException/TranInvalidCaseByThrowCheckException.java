package com.github.lybgeek.transcase.throwCheckException;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class TranInvalidCaseByThrowCheckException {

    @Autowired
    private UserService userService;


    @Transactional
    public boolean add(User user) throws FileNotFoundException {
        boolean isSuccess = userService.save(user);
        new FileInputStream("1.txt");
        return isSuccess;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) throws FileNotFoundException {
        boolean isSuccess = userService.save(user);
        new FileInputStream("1.txt");
        return isSuccess;
    }



}

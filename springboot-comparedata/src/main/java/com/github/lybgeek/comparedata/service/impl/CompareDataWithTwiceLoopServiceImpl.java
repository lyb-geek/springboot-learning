package com.github.lybgeek.comparedata.service.impl;


import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.service.BaseCompareDataService;
import com.github.lybgeek.comparedata.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompareDataWithTwiceLoopServiceImpl extends BaseCompareDataService {
    
    @Override
    public void compareAndSave(List<User> users, List<MockUser> mockUsers) {
        System.out.println("CompareDataWithTwiceLoopServiceImpl---start");
        List<User> addUsers = new ArrayList<>();
        List<User> updateUsers = new ArrayList<>();
        for (MockUser mockUser : mockUsers) {
            for (User user : users) {
                if(mockUser.getUsername().equals(user.getUsername())){
                    int id = user.getId();
                    BeanUtils.copyProperties(mockUser,user);
                    user.setId(id);
                    updateUsers.add(user);
                }else{
                    User newUser = new User();
                    BeanUtils.copyProperties(mockUser,newUser);
                    addUsers.add(newUser);
                }
            }
        }

        System.out.println("CompareDataWithTwiceLoopServiceImpl----------:" + updateUsers.size());
        System.out.println("CompareDataWithTwiceLoopServiceImpl----------:" + addUsers.size());

        System.out.println("CompareDataWithTwiceLoopServiceImpl---end");


    }
}

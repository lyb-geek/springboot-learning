package com.github.lybgeek.comparedata.service.impl;


import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.service.BaseCompareDataService;
import com.github.lybgeek.comparedata.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompareDataWithMapServiceImpl extends BaseCompareDataService {


    @Override
    public void compareAndSave(List<User> users, List<MockUser> mockUsers) {
        System.out.println("CompareDataWithMapServiceImpl---start");
        Map<String,User> originUserMap = getOriginUserMap(users);
        List<User> addUsers = new ArrayList<>();
        List<User> updateUsers = new ArrayList<>();
        for (MockUser mockUser : mockUsers) {
             if(originUserMap.containsKey(mockUser.getUsername())){
                 User user = originUserMap.get(mockUser.getUsername());
                 int id = user.getId();
                 BeanUtils.copyProperties(mockUser,user);
                 user.setId(id);
                 updateUsers.add(user);
             }else{
                 User user = new User();
                 BeanUtils.copyProperties(mockUser,user);
                 addUsers.add(user);
             }
        }

        System.out.println("CompareDataWithMapServiceImpl----------:" + updateUsers.size());
        System.out.println("CompareDataWithMapServiceImpl----------:" + addUsers.size());

        System.out.println("CompareDataWithMapServiceImpl---end");
    }

    private Map<String,User> getOriginUserMap(List<User> users){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("CompareDataWithMapServiceImpl-getOriginUserMap");
        Map<String,User> originUserMap = new HashMap<>();
        for (User user : users) {
            originUserMap.put(user.getUsername(),user);
        }
        stopWatch.stop();
        System.out.println("CompareDataWithMapServiceImpl--getOriginUserMap-- costTime:" + stopWatch.getTotalTimeSeconds());
        return originUserMap;
    }
}

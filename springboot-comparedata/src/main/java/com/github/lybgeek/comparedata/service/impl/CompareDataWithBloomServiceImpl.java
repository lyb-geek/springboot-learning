package com.github.lybgeek.comparedata.service.impl;


import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.service.BaseCompareDataService;
import com.github.lybgeek.comparedata.user.entity.User;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CompareDataWithBloomServiceImpl extends BaseCompareDataService {


    private Map<String,User> originUserMap = new ConcurrentHashMap<>();


    @Override
    public void compareAndSave(List<User> users,List<MockUser> mockUsers){
        System.out.println("CompareDataWithBloomServiceImpl---start");
        List<User> addUsers = new ArrayList<>();
        List<User> updateUsers = new ArrayList<>();
        BloomFilter<String> bloomFilter = getUserNameBloomFilter(users);
        for (MockUser mockUser : mockUsers) {
            boolean isExist = bloomFilter.mightContain(mockUser.getUsername());
            //更新
            if(isExist){
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

        System.out.println("CompareDataWithBloomServiceImpl----------:" + updateUsers.size());
        System.out.println("CompareDataWithBloomServiceImpl----------:" + addUsers.size());

        System.out.println("CompareDataWithBloomServiceImpl---start");

    }



    private BloomFilter<String> getUserNameBloomFilter(List<User> users){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("CompareDataWithBloomServiceImpl-getUserNameBloomFilter");
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), users.size(), 0.001);
        for (User user : users) {
            bloomFilter.put(user.getUsername());
            originUserMap.put(user.getUsername(),user);
        }
        stopWatch.stop();
        System.out.println("CompareDataWithBloomServiceImpl--getUserNameBloomFilter-- costTime:" + stopWatch.getTotalTimeSeconds());
        return bloomFilter;
    }
}

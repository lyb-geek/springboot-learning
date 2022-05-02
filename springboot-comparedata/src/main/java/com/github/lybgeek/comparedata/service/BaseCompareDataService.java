package com.github.lybgeek.comparedata.service;


import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.mockuser.service.MockUserService;
import com.github.lybgeek.comparedata.user.entity.User;
import com.github.lybgeek.comparedata.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.List;

public abstract class BaseCompareDataService implements CompareDataService{

    @Autowired
    protected MockUserService mockUserService;

    @Autowired
    protected UserService userService;

    public void compareAndSave() {
        StopWatch stopWatchUser = new StopWatch();
        stopWatchUser.start("stopWatchUser");
        List<User> users = userService.list();
        stopWatchUser.stop();;
        System.out.println("stopWatchUser -> costTime:" + stopWatchUser.getTotalTimeSeconds());

        List<MockUser> mockUsers = mockUserService.list();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("compareAndSave");
        compareAndSave(users,mockUsers);
        stopWatch.stop();
        System.out.println("costTime:" + stopWatch.getTotalTimeSeconds());

    }



}

package com.github.lybgeek.transcase.serviceWithoutInjectSpring;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;

public class TranInvalidCaseWithoutInjectSpring {

    private UserService userService;

    public TranInvalidCaseWithoutInjectSpring(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public boolean add(User user){
        boolean isSuccess = userService.save(user);
        int i = 1 % 0;
        return isSuccess;
    }
}

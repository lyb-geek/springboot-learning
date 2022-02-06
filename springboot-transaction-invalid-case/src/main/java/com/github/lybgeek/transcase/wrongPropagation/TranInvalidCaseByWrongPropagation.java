package com.github.lybgeek.transcase.wrongPropagation;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranInvalidCaseByWrongPropagation {

    @Autowired
    private UserService userService;


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean save(User user) {
        boolean isSuccess = userService.save(user);
        try {
            int i = 1 % 0;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return isSuccess;
    }
}

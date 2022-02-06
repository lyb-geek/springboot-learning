package com.github.lybgeek.transcase.trycatch;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class TranInvalidCaseWithCatchException {


    @Autowired
    private UserService userService;


    @Transactional
    public boolean save(User user) {
        boolean isSuccess = userService.save(user);
        try {
            int i = 1 % 0;
        } catch (Exception e) {
           throw new RuntimeException();
        }
        return isSuccess;
    }


    @Transactional
    public boolean addWithRollBack(User user) {
        boolean isSuccess = userService.save(user);
        try {
            int i = 1 % 0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return isSuccess;
    }


    @Transactional
    public boolean add(User user) {
        boolean isSuccess = userService.save(user);
        try {
            int i = 1 % 0;
        } catch (Exception e) {

        }
        return isSuccess;
    }

}

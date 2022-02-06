package com.github.lybgeek.transcase.multThread;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TranInvalidCaseWithMultThread {


    @Autowired
    private UserService userService;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) throws ExecutionException, InterruptedException {

        Future<Boolean> future = executorService.submit(() -> {
            boolean isSuccess = userService.save(user);
            try {
                int i = 1 % 0;
            } catch (Exception e) {
                throw new Exception();
            }
            return isSuccess;
        });
        return future.get();


    }
}

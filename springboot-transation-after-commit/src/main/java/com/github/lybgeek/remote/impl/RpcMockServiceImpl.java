package com.github.lybgeek.remote.impl;


import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.AjaxResult;
import com.github.lybgeek.remote.RpcMockService;
import com.github.lybgeek.transactional.annotation.AfterCommitTransationCallBack;
import com.github.lybgeek.user.constant.Constant;
import com.github.lybgeek.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RpcMockServiceImpl implements RpcMockService {

    @Override
    @AfterCommitTransationCallBack
    public AjaxResult<String> mockLongTimeNotity(User user) {
        log.info("user->{}",user);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return AjaxResult.success("模拟耗时调用");
    }

    @Override
    @AfterCommitTransationCallBack
    public AjaxResult<String> mockExceptionCall(User user) {
        if(Constant.MOCK_EXCEPTION_USER.equals(user.getUsername())){
            throw new BizException("业务异常");
        }
        return AjaxResult.success("模拟业务异常调用");
    }
}

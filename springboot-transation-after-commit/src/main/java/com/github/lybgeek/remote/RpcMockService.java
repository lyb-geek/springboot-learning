package com.github.lybgeek.remote;


import com.github.lybgeek.common.model.AjaxResult;
import com.github.lybgeek.user.entity.User;

public interface RpcMockService {

    /**
     * 模拟长耗时操作
     * @param user
     * @return
     */
    AjaxResult<String> mockLongTimeNotity(User user);

    /**
     * 模拟RPC异常调用
     * @param user
     * @return
     */
    AjaxResult<String> mockExceptionCall(User user);

}

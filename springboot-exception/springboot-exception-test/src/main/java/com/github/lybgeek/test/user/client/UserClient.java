package com.github.lybgeek.test.user.client;


import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.github.lybgeek.resp.model.AjaxResult;
import com.github.lybgeek.test.user.entity.User;

public interface UserClient {
    String baseUrl = "http://localhost:8080/user";

    @Get(url = baseUrl + "/{0}")
    AjaxResult<User> getById(Long id);

    @Post(url = baseUrl)
    AjaxResult<Boolean> save(@JSONBody User user);
}

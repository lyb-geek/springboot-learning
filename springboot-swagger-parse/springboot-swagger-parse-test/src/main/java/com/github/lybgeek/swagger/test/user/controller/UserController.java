package com.github.lybgeek.swagger.test.user.controller;


import com.github.lybgeek.swagger.test.dto.RPCResult;
import com.github.lybgeek.swagger.test.user.dto.UserInfoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "用户管理接口")
@RequestMapping("user")
public class UserController {


    @ApiOperation(value = "根据ID查找用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path")
    @GetMapping("/{id}")
    public RPCResult<UserInfoDTO> getUserInfo(@PathVariable Integer id){
         UserInfoDTO userInfoDTO = new UserInfoDTO();
         userInfoDTO.setUserName("lybgeek");
         userInfoDTO.setUserAlias("lybgeek");
         userInfoDTO.setCardNumber("123456789012345678");
         userInfoDTO.setId(id);
         return RPCResult.success(userInfoDTO);

    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    public RPCResult<UserInfoDTO> saveUserInfo(UserInfoDTO userInfoDTO){
       return RPCResult.success(userInfoDTO);
    }


    @ApiOperation(value = "新增用户-1")
    @PostMapping("/add")
    public RPCResult<UserInfoDTO> addUserInfo(@RequestBody UserInfoDTO userInfoDTO){
        return RPCResult.success(userInfoDTO);
    }


    @ApiOperation(value = "查询用户列表")
    @GetMapping("/list")
    public RPCResult<List<UserInfoDTO>> list(){
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUserName("lybgeek"+i);
            userInfoDTO.setUserAlias("lybgeek"+i);
            userInfoDTO.setCardNumber("123456789012345678");
            userInfoDTO.setId(i);
            userInfoDTOList.add(userInfoDTO);
        }
        return RPCResult.success(userInfoDTOList);
    }



}

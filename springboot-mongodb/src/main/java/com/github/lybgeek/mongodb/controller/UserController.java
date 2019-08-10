package com.github.lybgeek.mongodb.controller;

import com.github.lybgeek.mongodb.common.dto.Result;
import com.github.lybgeek.mongodb.common.page.PageQuery;
import com.github.lybgeek.mongodb.common.page.PageResult;
import com.github.lybgeek.mongodb.dto.UserDTO;
import com.github.lybgeek.mongodb.model.User;
import com.github.lybgeek.mongodb.service.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


  @Autowired
  private UserService userService;

  @PostMapping(value="/add")
  public Result<UserDTO> addUser(@Valid UserDTO userDTO, BindingResult bindingResult){
    Result<UserDTO> result = new Result<>();
    if (bindingResult.hasErrors()){
      return getUserFailResult(bindingResult, result);
    }
    saveUser(userDTO, result);

    return result;

  }


  @PostMapping(value="/update")
  public Result<UserDTO> upadteUser(@Valid UserDTO userDTO, BindingResult bindingResult){
    Result<UserDTO> result = new Result<>();
    if(userDTO.getId() == null){
      result.setStatus(Result.fail);
      result.setMessage("id不能为空");
      return result;
    }
    if (bindingResult.hasErrors()){
      return getUserFailResult(bindingResult, result);
    }
    saveUser(userDTO, result);
    return result;


  }


  @PostMapping(value="/del/{id}")
  public Result<Boolean> delAuthor(@PathVariable("id")Long id){
    Result<Boolean> result = new Result<>();
    Boolean delFlag = userService.delUserById(id);
    result.setData(delFlag);
    return result;

  }

  @PostMapping(value="/list")
  public Result<List<User>> listUsers(UserDTO userDTO){
    Result<List<User>> result = new Result<>();
    List<User> users = userService.listUsers(userDTO);
    result.setData(users);
    return result;
  }

  @PostMapping(value="/page")
  public Result<PageResult<User>> pageUsers(UserDTO userDTO,Integer pageNum,Integer pageSize){

    PageQuery<UserDTO> pageQuery = new PageQuery<>();
    pageQuery.setPageNum(pageNum);
    pageQuery.setPageSize(pageSize);
    pageQuery.setQueryParams(userDTO);
    Result<PageResult<User>> result = new Result<>();
    PageResult<User> users = userService.pageUsers(pageQuery);
    result.setData(users);
    return result;
  }

  private Result<UserDTO> getUserFailResult(BindingResult bindingResult, Result<UserDTO> result) {
    result.setStatus(Result.fail);
    List<ObjectError> errorList = bindingResult.getAllErrors();
    StringBuilder messages = new StringBuilder();
    errorList.forEach(error->{
      messages.append(error.getDefaultMessage()).append(";");
    });
    result.setMessage(messages.toString());
    return result;
  }

  private void saveUser(@Valid UserDTO userDTO, Result<UserDTO> result) {

    try {
      UserDTO dbUserDTO = userService.saveUser(userDTO);
      result.setData(dbUserDTO);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      result.setMessage(e.getMessage());
      result.setStatus(Result.fail);

    }
  }

}

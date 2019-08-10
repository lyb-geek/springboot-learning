package com.github.lybgeek.mongodb.service.impl;

import com.github.lybgeek.mongodb.common.page.PageQuery;
import com.github.lybgeek.mongodb.common.page.PageResult;
import com.github.lybgeek.mongodb.dao.UserDao;
import com.github.lybgeek.mongodb.dto.UserDTO;
import com.github.lybgeek.mongodb.enu.Gender;
import com.github.lybgeek.mongodb.enu.ValidateTransaction;
import com.github.lybgeek.mongodb.model.User;
import com.github.lybgeek.mongodb.service.UserService;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UserDao userDao;

  @Override
  @Transactional
  public UserDTO saveUser(UserDTO userDTO) {

    User user = getUser(userDTO);

    User dbUser = userDao.saveUser(user);
    if(ValidateTransaction.YES.getValue().equals(userDTO.getValidateRollBack())) {
      throw new RuntimeException("验证事务回滚");
    }
    UserDTO dbUserDTO = modelMapper.map(dbUser,UserDTO.class);
    dbUserDTO.setGender(dbUser.getGender().getValue());



    return dbUserDTO;
  }


  @Override
  @Transactional
  public boolean delUserById(Long id) {

    return userDao.delUserById(id);
  }

  @Override
  public List<User> listUsers(UserDTO userDTO) {
    User dbUser = getUser(userDTO);
    List<User> users = userDao.listUsers(dbUser);

//    if(!CollectionUtils.isEmpty(users)){
//      List<UserDTO> userDTOS = modelMapper.map(users,new TypeToken<List<UserDTO>>() {}.getType());
//      return userDTOS;
//    }

    return users;
  }

  @Override
  public PageResult<User> pageUsers(PageQuery<UserDTO> pageQuery) {
    UserDTO userDTO = pageQuery.getQueryParams();
    User dbUser = getUser(userDTO);
    PageQuery<User> dbPageQuery = new PageQuery<>();
    dbPageQuery.setQueryParams(dbUser);
    dbPageQuery.setPageSize(pageQuery.getPageSize() == null ? 10 : pageQuery.getPageSize());
    dbPageQuery.setPageNum(pageQuery.getPageNum() == null ? 1 : pageQuery.getPageNum());

    PageResult<User> pageResult = userDao.pageUsers(dbPageQuery);


    return pageResult;
  }


  private User getUser(UserDTO userDTO) {

    User user = modelMapper.map(userDTO,User.class);
    if(userDTO.getGender() != null) {
      user.setGender(Gender.getValue(userDTO.getGender()));
    }
    return user;
  }

}

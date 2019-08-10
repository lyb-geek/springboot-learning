package com.github.lybgeek;

import static org.junit.Assert.assertTrue;

import com.github.lybgeek.mongodb.dao.UserDao;
import com.github.lybgeek.mongodb.dto.UserDTO;
import com.github.lybgeek.mongodb.enu.Gender;
import com.github.lybgeek.mongodb.model.Address;
import com.github.lybgeek.mongodb.model.User;
import com.github.lybgeek.mongodb.service.UserService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MongodbApplicationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserDao userDao;

  @Test
  public void testAddUser(){

    Address address = Address.builder().province("北京").city("北京").detailAddr("test1").build();
    UserDTO userDTO = UserDTO.builder().userName("张三1").realName("张三").email("zhangsan@qq.com").password("1234561").address(address).gender(1).build();

    UserDTO dto = userService.saveUser(userDTO);

//    User dto = userDao.saveUser(User.builder().userName("张三1").email("zhangsan1@qq.com").password("1234561").gender(
//        Gender.MALE).address(address).build());

    Assert.assertNotNull(dto);

    System.out.println(dto);

  }


  @Test
  public void testList(){
    Address address = Address.builder().detailAddr("test1").build();
    User user = User.builder().userName("张三1").email("zhangsan1@qq.com").password("1234561").gender(
        Gender.MALE).address(address).build();

    List<User> users = userDao.listUsers(user);

    System.out.println(users);

  }
}

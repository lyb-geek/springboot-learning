package com.github.lybgeek.mongodb.dao;

import com.github.lybgeek.mongodb.common.page.MongoPageHelper;
import com.github.lybgeek.mongodb.common.page.PageQuery;
import com.github.lybgeek.mongodb.common.page.PageResult;
import com.github.lybgeek.mongodb.model.User;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private MongoPageHelper mongoPageHelper;

  public User saveUser(User user){
    return userRepository.save(user);
  }

  public boolean delUserById(Long id){
    userRepository.deleteById(id);
    return !userRepository.existsById(id);
  }

   public List<User> listUsers(User user){


     Query query = getQueryCondition(user);

     return mongoTemplate.find(query,User.class);

  }


  public PageResult<User> pageUsers(PageQuery<User> pageQuery){
    Query query = getQueryCondition(pageQuery.getQueryParams());

    return mongoPageHelper.pageQuery(query,User.class,pageQuery.getPageSize(),pageQuery.getPageNum());

  }

  private Query getQueryCondition(User user) {
    Query query = new Query();

    if(user.getId() != null){
      query.addCriteria(Criteria.where("_id").is(user.getId()));
    }

    if(StringUtils.isNotBlank(user.getEmail())){
      query.addCriteria(Criteria.where("email").is(user.getEmail()));
    }

    if(StringUtils.isNotBlank(user.getRealName())){
      query.addCriteria(new Criteria().orOperator(Criteria.where("userName").regex(user.getRealName()),Criteria.where("realName")).regex(user.getRealName()));
    }

    if(StringUtils.isNotBlank(user.getUserName())){
      query.addCriteria(new Criteria().orOperator(Criteria.where("userName").regex(user.getUserName()),Criteria.where("realName")).regex(user.getUserName()));
    }

    if(user.getGender() != null){
      query.addCriteria(Criteria.where("gender").is(user.getGender()));
    }

    if(user.getAddress() != null) {
      if (StringUtils.isNotBlank(user.getAddress().getCity())) {
        query.addCriteria(Criteria.where("address.city").is(user.getAddress().getCity()));
      }

      if (StringUtils.isNotBlank(user.getAddress().getProvince())) {
        query.addCriteria(Criteria.where("address.province").is(user.getAddress().getProvince()));
      }

      if (StringUtils.isNotBlank(user.getAddress().getDetailAddr())) {
        query.addCriteria(
            Criteria.where("address.detailAddr").regex(user.getAddress().getDetailAddr()));
      }
    }

    return query;
  }

}

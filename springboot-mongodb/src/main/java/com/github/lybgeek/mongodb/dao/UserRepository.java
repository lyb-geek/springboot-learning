package com.github.lybgeek.mongodb.dao;

import com.github.lybgeek.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Long> {

}

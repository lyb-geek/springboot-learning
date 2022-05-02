package com.github.lybgeek.comparedata.service;


import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.user.entity.User;

import java.util.List;

public interface CompareDataService {

   void compareAndSave(List<User> users, List<MockUser> mockUsers);
}

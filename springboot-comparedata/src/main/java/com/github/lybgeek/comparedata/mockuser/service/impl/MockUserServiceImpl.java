package com.github.lybgeek.comparedata.mockuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.lybgeek.comparedata.mockuser.dao.MockUserDao;
import com.github.lybgeek.comparedata.mockuser.entity.MockUser;
import com.github.lybgeek.comparedata.mockuser.service.MockUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MockUserServiceImpl extends ServiceImpl<MockUserDao, MockUser> implements MockUserService {



}

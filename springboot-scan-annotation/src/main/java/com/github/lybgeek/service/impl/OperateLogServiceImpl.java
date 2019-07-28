package com.github.lybgeek.service.impl;


import com.github.lybgeek.dao.OperateLogRepository;
import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {

  @Autowired
  private OperateLogRepository operateLogRepository;


  @Override
  public List<OperateLog> listOperateLogs() {

   return operateLogRepository.findAll();
  }

  @Override
  @Transactional(rollbackOn = RuntimeException.class)
  public void saveLog(OperateLog operateLog) {
     operateLogRepository.save(operateLog);

  }
}

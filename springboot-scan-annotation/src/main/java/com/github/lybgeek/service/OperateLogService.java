package com.github.lybgeek.service;

import com.github.lybgeek.model.OperateLog;

import java.util.List;

public interface OperateLogService {


  List<OperateLog> listOperateLogs();

  void saveLog(OperateLog log);

}

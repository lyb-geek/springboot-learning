package com.github.lybgeek.service;

import com.github.lybgeek.model.OperateLog;

import java.util.List;

public interface OperateLogService {

  void saveLog(OperateLog operateLog);

  List<OperateLog> listOperateLogs();

  void saveLog(String content);

}

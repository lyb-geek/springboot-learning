package com.github.lybgeek.service.impl;


import com.github.lybgeek.db.template.DbTemplate;
import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

  @Autowired
  private DbTemplate dbTemplate;

  @Override
  public void saveLog(OperateLog operateLog) {

    saveContent(operateLog.getContent());
  }

  private void saveContent(String content) {

    try {
      String sql = "insert into operate_log (content) values(?)";
      dbTemplate.update(sql,content);
    } catch (SQLException e) {
      log.error("saveLog error:"+e.getMessage(),e);
    }
  }

  @Override
  public List<OperateLog> listOperateLogs() {

    try {
      List<OperateLog> operateLogs = dbTemplate.query(
          "select * from operate_log order by create_time desc",
          (resultSet)->{
            List<OperateLog> logs = new ArrayList<>();
            while(resultSet.next()){
              OperateLog operateLog = new OperateLog();
              operateLog.setId(resultSet.getLong("id"));
              operateLog.setContent(resultSet.getString("content"));
              operateLog.setCreateTime(resultSet.getDate("create_time"));
              logs.add(operateLog);
            }
            return logs;
          }
      );
      return operateLogs;
    } catch (SQLException e) {
      log.error("listOperateLogs error:"+e.getMessage(),e);
    }
    return null;
  }

  @Override
  public void saveLog(String content) {
     saveContent(content);
  }
}

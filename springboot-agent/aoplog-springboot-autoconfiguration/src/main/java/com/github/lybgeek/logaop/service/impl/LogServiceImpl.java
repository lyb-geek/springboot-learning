package com.github.lybgeek.logaop.service.impl;


import com.github.lybgeek.logaop.constant.SqlConstant;
import com.github.lybgeek.logaop.entity.ServiceLog;
import com.github.lybgeek.logaop.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Slf4j
public class LogServiceImpl implements LogService {

    private final JdbcTemplate jdbcTemplate;

    public LogServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Async("serviceLogTaskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveLog(ServiceLog serviceLog) {
        String sql = "insert into service_log (service_name,method_name,req_args,resp_result,cost_time,status) values (?,?,?,?,?,?)";
        Object[] params = { serviceLog.getServiceName(), serviceLog.getMethodName(), serviceLog.getReqArgs(),serviceLog.getRespResult(),serviceLog.getCostTime(),serviceLog.getStatus() };
        int count = jdbcTemplate.update(sql,params);
        if(count > 0){
            log.info("插入成功");
            return true;
        }

        return false;

    }

    private boolean existTable(){
        String sql = "SELECT COUNT(*) from information_schema.tables WHERE table_schema = (SELECT DATABASE()) AND table_name = ? ";
        int count = jdbcTemplate.query(sql, resultSet -> {
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
            return null;
        },"service_log");

        return count > 0;
    }


    @PostConstruct
    private void initTable(){
        if(existTable()){
            return;
        }
        jdbcTemplate.update(SqlConstant.CREATE_TABLE_SQL);

    }


}

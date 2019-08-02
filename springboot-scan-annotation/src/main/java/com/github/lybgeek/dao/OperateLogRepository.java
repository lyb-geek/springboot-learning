package com.github.lybgeek.dao;

import com.github.lybgeek.model.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperateLogRepository extends JpaRepository<OperateLog,Long> {
}

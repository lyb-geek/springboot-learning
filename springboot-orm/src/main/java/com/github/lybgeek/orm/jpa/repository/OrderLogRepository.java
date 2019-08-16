package com.github.lybgeek.orm.jpa.repository;

import com.github.lybgeek.orm.jpa.model.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaSpecificationExecutor 可以用来执行复杂查询
 */
public interface OrderLogRepository extends JpaSpecificationExecutor<OrderLog>,JpaRepository<OrderLog,Long> {

}

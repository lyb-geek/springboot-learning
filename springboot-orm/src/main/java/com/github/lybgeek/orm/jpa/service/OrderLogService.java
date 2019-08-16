package com.github.lybgeek.orm.jpa.service;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import org.springframework.data.domain.Page;

public interface OrderLogService {

  void saveLog(OrderLog orderLog);

  PageResult<OrderLog> pageOrderLogs(PageQuery<OrderLog> pageQuery);

}

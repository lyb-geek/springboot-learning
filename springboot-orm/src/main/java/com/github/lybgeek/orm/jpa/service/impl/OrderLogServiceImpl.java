package com.github.lybgeek.orm.jpa.service.impl;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.jpa.dao.OrderLogDao;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import com.github.lybgeek.orm.jpa.service.OrderLogService;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderLogServiceImpl implements OrderLogService {

  @Autowired
  private OrderLogDao orderLogDao;

  @Override
  @Transactional
  public void saveLog(OrderLog orderLog) {
    orderLogDao.saveLog(orderLog);
  }

  @Override
  public PageResult<OrderLog> pageOrderLogs(PageQuery<OrderLog> pageQuery) {
    if(ObjectUtils.isEmpty(pageQuery.getPageNo())){
      pageQuery.setPageNo(1);
    }

    if(ObjectUtils.isEmpty(pageQuery.getPageSize())){
      pageQuery.setPageSize(10);
    }
    return orderLogDao.pageOrderLogs(pageQuery);
  }
}

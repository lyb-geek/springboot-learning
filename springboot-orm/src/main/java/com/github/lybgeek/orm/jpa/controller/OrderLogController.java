package com.github.lybgeek.orm.jpa.controller;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.common.model.Result;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import com.github.lybgeek.orm.jpa.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class OrderLogController {

  @Autowired
  private OrderLogService orderLogService;

  @RequestMapping(value="/page")
  public Result<PageResult<OrderLog>> pageOrderLogs(OrderLog orderLog,Integer pageNo,Integer pageSize){
    PageQuery pageQuery = new PageQuery<>().setPageSize(pageSize).setPageNo(pageNo).setQueryParams(orderLog);
    Result<PageResult<OrderLog>> result = new Result<>();
    PageResult<OrderLog> page = orderLogService.pageOrderLogs(pageQuery);
    result.setData(page);
    return result;
  }

}

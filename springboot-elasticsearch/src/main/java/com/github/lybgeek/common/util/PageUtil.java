package com.github.lybgeek.common.util;


import com.github.lybgeek.common.model.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;

public enum  PageUtil {
  INSTANCE;


  public <T> PageResult<T> getPage(Page<T> page){
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setList(page.getContent());
    setPageResult(page, pageResult);
    return pageResult;
  }

  private <T> void setPageResult(Page page, PageResult<T> pageResult) {

    pageResult.setPageNo(page.getPageable().getPageNumber());
    pageResult.setPageSize(page.getPageable().getPageSize());
    pageResult.setTotal(page.getTotalElements());
    pageResult.setTotalPages(page.getTotalPages());
  }



  public <T> PageResult<T> getPage(Page page, List<T> list){
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setList(list);
    setPageResult(page, pageResult);
    return pageResult;
  }


}

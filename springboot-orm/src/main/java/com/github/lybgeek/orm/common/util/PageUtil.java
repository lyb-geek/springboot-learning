package com.github.lybgeek.orm.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.lybgeek.orm.common.model.PageResult;
import java.util.List;
import org.springframework.data.domain.Page;

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

  public <T> PageResult<T> getPage(IPage page, List<T> list){
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setList(list);
    pageResult.setPageNo(Integer.valueOf(String.valueOf(page.getCurrent())));
    pageResult.setPageSize(Integer.valueOf(String.valueOf(page.getSize())));
    pageResult.setTotal(page.getTotal());
    pageResult.setTotalPages(Integer.valueOf(String.valueOf(page.getPages())));
    return pageResult;
  }

  public <T> PageResult<T> getPage(Page page, List<T> list){
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setList(list);
    setPageResult(page, pageResult);
    return pageResult;
  }
}

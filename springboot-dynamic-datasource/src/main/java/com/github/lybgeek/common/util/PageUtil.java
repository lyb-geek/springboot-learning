package com.github.lybgeek.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.lybgeek.common.model.PageResult;
import java.util.List;

public enum  PageUtil {
  INSTANCE;



  public <T> PageResult<T> getPage(IPage page, List<T> list){
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setList(list);
    pageResult.setPageNo(Integer.valueOf(String.valueOf(page.getCurrent())));
    pageResult.setPageSize(Integer.valueOf(String.valueOf(page.getSize())));
    pageResult.setTotal(page.getTotal());
    pageResult.setTotalPages(Integer.valueOf(String.valueOf(page.getPages())));
    return pageResult;
  }


}

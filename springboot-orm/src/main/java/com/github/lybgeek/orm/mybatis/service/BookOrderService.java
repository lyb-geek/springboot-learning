package com.github.lybgeek.orm.mybatis.service;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.mybatis.dto.BookOrderDTO;

import java.util.List;

public interface BookOrderService {

    BookOrderDTO createBookOrder(BookOrderDTO bookOrderDTO);

    BookOrderDTO editBookOrder(BookOrderDTO bookOrderDTO);

    boolean delBookOrderById(Long id);

    PageResult<BookOrderDTO> pageBookOrder(PageQuery<BookOrderDTO> pageQuery);

    List<BookOrderDTO> listBookOrders(BookOrderDTO bookOrderDTO);
}

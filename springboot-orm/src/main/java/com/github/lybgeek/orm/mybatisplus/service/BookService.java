package com.github.lybgeek.orm.mybatisplus.service;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.mybatisplus.dto.BookDTO;
import com.github.lybgeek.orm.mybatisplus.model.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
public interface BookService extends IService<Book> {

  BookDTO addBook(BookDTO bookDTO);

  BookDTO editBook(BookDTO bookDTO);

  boolean delBookById(Long id);

  PageResult<BookDTO> pageBook(PageQuery<BookDTO> pageQuery);

  List<BookDTO> listBooks(BookDTO bookDTO);

  BookDTO getBookById(Long id);

  int updateStockById(Long id,Integer count);

}

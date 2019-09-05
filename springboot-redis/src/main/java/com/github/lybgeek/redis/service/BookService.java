package com.github.lybgeek.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;

import com.github.lybgeek.redis.dto.BookDTO;
import com.github.lybgeek.redis.model.Book;
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

  int updateStockById(Long id, Integer count);

  BookDTO getBookByName(String bookName);

}

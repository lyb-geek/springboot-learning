package com.github.lybgeek.httpclient.service;


import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.httpclient.annotation.Remote;
import com.github.lybgeek.httpclient.annotation.RemoteHeader;
import com.github.lybgeek.httpclient.annotation.RemotePathParam;
import com.github.lybgeek.httpclient.annotation.RemoteRequestMapping;
import com.github.lybgeek.httpclient.dto.BookDTO;
import com.github.lybgeek.httpclient.enu.HttpclientTypeEnum;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
@Remote(url = "http://localhost:8080/api/1.0.0/book",headers = {@RemoteHeader(name = "AccessToken",value = "123456")})
public interface BookService {

  @RemoteRequestMapping(path = "/add")
  BookDTO addBook(BookDTO bookDTO);

  @RemoteRequestMapping(path = "/update",type = HttpclientTypeEnum.REST_TEMPLATE)
  BookDTO editBook(BookDTO bookDTO);

  @RemoteRequestMapping(path = "/del/{id}",type = HttpclientTypeEnum.WEB_CLIENT)
  boolean delBookById(@RemotePathParam(value = "id") Long id);

  @RemoteRequestMapping(path = "/page")
  PageResult<BookDTO> pageBook(PageQuery<BookDTO> pageQuery);

  @RemoteRequestMapping(path = "/list")
  List<BookDTO> listBooks(BookDTO bookDTO);




}

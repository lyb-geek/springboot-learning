package com.github.lybgeek.redis.controller;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.model.Result;
import com.github.lybgeek.common.util.ResultUtil;

import com.github.lybgeek.redis.dto.BookDTO;
import com.github.lybgeek.redis.service.BookService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

  @Autowired
  private BookService bookService;


  @PostMapping(value="/add")
  public Result<BookDTO> addBook(@Valid BookDTO bookDTO, BindingResult bindingResult){
    Result<BookDTO> result = new Result<>();
    if (bindingResult.hasErrors()){
      return ResultUtil.INSTANCE.getFailResult(bindingResult, result);
    }
    try {
      BookDTO book = bookService.addBook(bookDTO);
      result.setData(book);
    } catch (Exception e) {
      log.error("addBook error:"+e.getMessage(),e);
      result.setStatus(Result.fail);
      result.setMessage(e.getMessage());
    }

    return result;

  }


  @PostMapping(value="/update")
  public Result<BookDTO> upadteBook(BookDTO bookDTO){
    Result<BookDTO> result = new Result<>();
    if(bookDTO.getId() == null){
      result.setStatus(Result.fail);
      result.setMessage("id不能为空");
      return result;
    }
    BookDTO book = bookService.editBook(bookDTO);
    result.setData(book);
    return result;


  }


  @PostMapping(value="/del/{id}")
  public Result<Boolean> delBook(@PathVariable("id")Long id){
    Result<Boolean> result = new Result<>();
    Boolean delFlag = bookService.delBookById(id);
    result.setData(delFlag);
    return result;

  }

  @PostMapping(value="/list")
  public Result<List<BookDTO>> listUsers(BookDTO bookDTO){
    Result<List<BookDTO>> result = new Result<>();
    List<BookDTO> books = bookService.listBooks(bookDTO);
    result.setData(books);
    return result;
  }

  @PostMapping(value="/page")
  public Result<PageResult<BookDTO>> pageUsers(BookDTO bookDTO,Integer pageNo,Integer pageSize){

    PageQuery<BookDTO> pageQuery = new PageQuery<>();
    pageQuery.setPageNo(pageNo);
    pageQuery.setPageSize(pageSize);
    pageQuery.setQueryParams(bookDTO);
    Result<PageResult<BookDTO>> result = new Result<>();
    PageResult<BookDTO> books = bookService.pageBook(pageQuery);
    result.setData(books);
    return result;
  }






}

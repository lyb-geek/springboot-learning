package com.github.lybgeek;

import com.alibaba.fastjson.JSON;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.redis.dto.BookDTO;
import com.github.lybgeek.redis.service.BookService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisApplicationTest {

  @Autowired
  private BookService bookService;

  @Test
  public void testAddBook() {

    for (int i = 1; i <= 10; i++) {
      BookDTO bookDTO = BookDTO.builder().bookName("java从入门到精通（第" + i + ")版")
          .author("张三" + i).description("java从入门到精通（第" + i + ")版,热门系列")
          .price(BigDecimal.valueOf(i * 10)).stock(i).build();
      bookDTO = bookService.addBook(bookDTO);
      System.out.println(bookDTO);
    }
  }

  @Test
  public void testSaveBook() {

    BookDTO bookDTO = BookDTO.builder().bookName("netty从入门到精通版第一版")
        .author("张三").description("netty从入门到精通,热门系列")
        .price(BigDecimal.valueOf(10)).stock(10).build();
    bookDTO = bookService.addBook(bookDTO);
    System.out.println(bookDTO);
  }

  @Test
  public void testUpdateBook() {

    BookDTO bookDTO = BookDTO.builder().id(9L).bookName("docker实战").author("大神")
        .description("docker实战系列教程").price(BigDecimal.valueOf(23.2)).stock(1).build();
    bookDTO = bookService.editBook(bookDTO);
    System.out.println(bookDTO);
  }

  @Test
  public void testDelBook() {

    boolean del = bookService.delBookById(21L);
    Assert.assertTrue(del);
  }

  @Test
  public void testPageBook() {

    PageQuery pageQuery = new PageQuery<>().setPageNo(1).setPageSize(5);
    BookDTO bookDTO = new BookDTO();
    bookDTO.setAuthor("张三");
    //   bookDTO.setBookName("图解Http");
    //   bookDTO.setId(4L);
    pageQuery.setQueryParams(bookDTO);

    PageResult<BookDTO> pageResult = bookService.pageBook(pageQuery);

    if (pageResult != null) {
      pageResult.getList().forEach(book -> System.out.println(book));
    }

  }

  @Test
  public void testListBook() {

    BookDTO bookDTO = new BookDTO();
    bookDTO.setAuthor("张三");
    bookDTO.setBookName("java");
    //       bookDTO.setId(5L);
    List<BookDTO> bookDTOS = bookService.listBooks(bookDTO);
    bookDTOS.forEach(book -> System.out.println(book));
  }


  @Test
  public void testGetBook(){
    BookDTO bookDTO = bookService.getBookById(1L);
    Assert.assertNotNull(bookDTO);
    System.out.println(bookDTO);
  }

  @Test
  public void testUpdateStock() throws Exception{
    for(int i = 0; i < 2; i++){
      Thread t =   new Thread(()->{
        bookService.updateStockById(10L,6);
      });
      t.start();
      t.join();
    }
  }

}

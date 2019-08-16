package com.github.lybgeek;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import com.github.lybgeek.orm.jpa.service.OrderLogService;
import com.github.lybgeek.orm.mybatisplus.dto.BookDTO;
import com.github.lybgeek.orm.mybatisplus.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrmApplicationTest {
    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private BookService bookService;

    @Test
    public void saveOrderLog(){

        for(int i = 0; i < 10; i++){
            OrderLog orderLog = OrderLog.builder().orderId(Long.valueOf(i+1)).orderName("订单"+(i+1)).orderContent("这是第"+(i+1)+"份订单").build();
            orderLogService.saveLog(orderLog);
        }

    }

    @Test
    public void testUpdateOrderLog(){
        OrderLog orderLog = new OrderLog();
        orderLog.setId(2l);
        orderLog.setOrderName("超级玛利亚的订单");
        orderLogService.saveLog(orderLog);
    }

    @Test
    public void testPageOrderLog(){
        OrderLog orderLog = new OrderLog();
        //orderLog.setOrderId(1L);
        orderLog.setOrderName("超级");
        PageQuery pageQuery = new PageQuery<>().setPageNo(1).setPageSize(5);
        pageQuery.setQueryParams(orderLog);
        PageResult<OrderLog> orderLogPage = orderLogService.pageOrderLogs(pageQuery);

        orderLogPage.getList().forEach(log -> System.out.println(log));
    }

    @Test
    public void testAddBook(){
        for(int i = 1; i <= 10; i++){
            BookDTO bookDTO = BookDTO.builder().bookName("java从入门到精通（第"+i+")版")
                .author("张三"+i).description("java从入门到精通（第"+i+")版,热门系列").price(BigDecimal.valueOf(i*10)).stock(i).build();
            bookDTO = bookService.addBook(bookDTO);
            System.out.println(bookDTO);
        }
    }

    @Test
    public void testUpdateBook(){
        BookDTO bookDTO = BookDTO.builder().id(1L).bookName("图解Http").author("大神").description("http入门系列教程").price(BigDecimal.valueOf(23.2)).stock(20).build();
        bookDTO =  bookService.editBook(bookDTO);
        System.out.println(bookDTO);
    }

    @Test
    public void testDelBook(){
        boolean del = bookService.delBookById(2L);
        Assert.assertTrue(del);
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

    @Test
    public void testPageStock(){
        PageQuery pageQuery = new PageQuery<>().setPageNo(1).setPageSize(5);
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("张三");
     //   bookDTO.setBookName("图解Http");
     //   bookDTO.setId(4L);
        pageQuery.setQueryParams(bookDTO);

        PageResult<BookDTO> pageResult = bookService.pageBook(pageQuery);

        if(pageResult != null){
            pageResult.getList().forEach(book -> System.out.println(book));
        }

    }


    @Test
    public void testListBook(){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("张三");
        bookDTO.setBookName("java");
 //       bookDTO.setId(5L);
        List<BookDTO> bookDTOS = bookService.listBooks(bookDTO);
        bookDTOS.forEach(book-> System.out.println(book));
    }

}

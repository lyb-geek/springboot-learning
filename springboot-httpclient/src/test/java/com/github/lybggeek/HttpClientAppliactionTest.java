package com.github.lybggeek;

import com.github.lybgeek.HttpClientApplication;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.httpclient.dto.BookDTO;
import com.github.lybgeek.httpclient.service.BookService;
import com.github.lybgeek.httpclient.util.HttpClientUtil;
import com.github.lybgeek.httpclient.util.RestTemplateUtil;
import com.github.lybgeek.httpclient.util.WebClientUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = HttpClientApplication.class)
@RunWith(value=SpringRunner.class)
public class HttpClientAppliactionTest {

    @Autowired
    private HttpClientUtil httpClientUtil;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private WebClientUtil webClientUtil;

    @Autowired
    private BookService bookService;


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
        BookDTO bookDTO = BookDTO.builder().id(1L).bookName("图解Http").author("大神").description("http入门系列教程").price(BigDecimal.valueOf(23.2)).stock(1).build();
        bookDTO =  bookService.editBook(bookDTO);
        System.out.println(bookDTO);
    }

    @Test
    public void testDelBook(){
        boolean del = bookService.delBookById(4L);
        Assert.assertTrue(del);
    }

    @Test
    public void testPageBook(){
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





    @Test
    public void testGetByRestemplate(){
        String url = "http://localhost:8080/api/1.0.0/book/get/1";
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        ResponseEntity<String> response =  restTemplateUtil.post(url,headers,null,String.class,new Object[]{});
        System.out.println(response.getBody());
    }

    @Test
    public void testListByResttemplate(){
        String url = "http://localhost:8080/api/1.0.0/book/list";
        Map<String,String> param = new HashMap<>();
        param.put("bookName","测试");
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        ResponseEntity<String> response =  restTemplateUtil.post(url,headers,param,String.class,new Object[]{});
        System.out.println(response.getBody());

    }

    @Test
    public void testUpdateByResttemplate(){
        String url = "http://localhost:8080/api/1.0.0/book/update";
        Map<String,String> param = new HashMap<>();
        param.put("bookName","测试驱动");
        param.put("id","3");
        param.put("author","小李");
        param.put("price","20.3");
        param.put("stock","20");
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        String response =  httpClientUtil.doPost(url,param,headers);
        System.out.println(response);

    }

    @Test
    public void testGetByHttpClient(){
        String url = "http://localhost:8080/api/1.0.0/book/get/1";
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        String result = httpClientUtil.doPost(url,null,headers);
        System.out.println(result);
    }

    @Test
    public void testListByHttpClient(){
        String url = "http://localhost:8080/api/1.0.0/book/list";
        Map<String,String> param = new HashMap<>();
        param.put("bookName","测试");
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        String result = httpClientUtil.doPost(url,param,headers);
        System.out.println(result);

    }

    @Test
    public void testGetByWebClient(){
        String url = "http://localhost:8080/api/1.0.0/book/get/1";
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        String result = webClientUtil.post(new HashMap<>(),url,headers,String.class);
        System.out.println(result);
    }

    @Test
    public void testListByWebClient(){
        String url = "http://localhost:8080/api/1.0.0/book/list";
        Map<String,String> param = new HashMap<>();
        param.put("bookName","测试");
        Map<String,String> headers = new HashMap<>();
        headers.put("AccessToken","123456");
        String result = webClientUtil.post(param,url,headers,String.class);
        System.out.println(result);

    }


}

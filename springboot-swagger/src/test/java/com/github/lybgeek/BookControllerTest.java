package com.github.lybgeek;

import com.github.lybgeek.swagger.constant.Constant;
import com.github.lybgeek.swagger.dto.BookDTO;
import com.github.lybgeek.swagger.service.BookService;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(value = SpringRunner.class)
@AutoConfigureMockMvc
//controller单元测试可以参考：https://www.jianshu.com/p/13408dd4bef7 和 https://www.cnblogs.com/caofanqi/p/10836459.html
public class BookControllerTest {
    //https://github.com/jacek99/structlog4j ,结构化日志
    private ILogger log = SLoggerFactory.getLogger(BookControllerTest.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    //模拟出一个bookService
    @MockBean
    private BookService bookService;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void testAddBook() throws Exception{
        BookDTO params = BookDTO.builder().bookName("测试驱动").author("张三").price(BigDecimal.valueOf(20.1)).stock(10).build();
        log.info("params",params);
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0.0/book/add").header(Constant.ACCESS_TOKEN,Constant.ACCESS_TOKEN_PASS_VALUE)
                .param("bookName","测试驱动")
                .param("author","战三")
                .param("price","20.1")
                .param("stock","10")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
       log.info("输出结构化日志","result",result);
    }


    @Test
    public void testGetById() throws Exception{
        //模拟bookService.findByUserId(1)的行为
        when(bookService.getBookById(1L)).thenReturn(BookDTO.builder().id(1L).bookName("mock实战").author("大神").description("mock").price(BigDecimal.valueOf(10)).stock(1).build());
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/1.0.0/book/get/1").cookie(new Cookie(Constant.ACCESS_TOKEN,Constant.ACCESS_TOKEN_PASS_VALUE)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("data.bookName").value("mock实战"))
                .andReturn().getResponse().getContentAsString();
        log.info("输出结构化日志","result",result);
    }

}

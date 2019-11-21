package com.github.lybgeek;

import com.github.lybgeek.downgrade.service.HelloService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class DowngradeApplicationTest {

  @Autowired
  @Qualifier("helloService")
  private HelloService helloService;

  private ExecutorService executorService = Executors.newFixedThreadPool(5);

  private ExecutorService controllerExecutorService;

  private AtomicInteger atomicInteger = new AtomicInteger(0);

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setup(){
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    controllerExecutorService = Executors.newCachedThreadPool(r -> {
      Thread thread = new Thread(r);
      thread.setName("downgrade-"+atomicInteger.getAndDecrement());
      return thread;
    });
  }

  private void join(){
    for(;;);
  }

  @Test
  public void testDowngrade(){
    for(int i = 0; i < 5; i++){
      int index = i;
      executorService.submit(()->{
        String hello = helloService.hello("zhangsan"+index);
        System.out.println(hello);
      });
    }

    executorService.shutdown();
    join();
  }

  @Test
  public void changeThreshold()throws Exception{
    String result = mockMvc.perform(MockMvcRequestBuilders.get("/change/{resouceId}/{maxThreshold}/","hello",10).characterEncoding("utf-8"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn().getResponse().getContentAsString();
    System.out.println("修改限流阈值:"+result);
  }

  @Test
  public void testHello()throws Exception{
    this.hello(1);
  }


  @Test
  public void testHelloDowngrade()throws Exception{
    this.helloThread();
    Thread.sleep(3000);
    System.out.println("-----------------------------------阈值调整------------------------------------");
    this.changeThreshold();
    System.out.println("-----------------------------------阈值调整后------------------------------------");
    this.helloThread();
    controllerExecutorService.shutdown();
    join();
  }


  private void helloThread(){
    for(int i = 0; i < 5; i++){
      int index = i;
      controllerExecutorService.execute(()->{
        try {
          hello(index);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }
  }


  private void hello(int index)throws Exception{
     String userName = "zhangsan_"+index;
     String result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/{userName}/",userName).characterEncoding("utf-8"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn().getResponse().getContentAsString();
     System.out.println(result);
  }

}

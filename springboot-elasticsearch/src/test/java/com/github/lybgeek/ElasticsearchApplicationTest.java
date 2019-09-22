package com.github.lybgeek;

import com.github.lybgeek.common.elasticsearch.util.ElasticsearchHelper;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.elasticsearch.constant.ElasticsearchConstant;
import com.github.lybgeek.elasticsearch.model.ShortUrlVO;
import com.github.lybgeek.elasticsearch.service.CustomShortUrlEsService;
import com.github.lybgeek.elasticsearch.service.ShortUrlEsService;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.dto.ShortUrlHelperDTO;
import com.github.lybgeek.shorturl.service.ShortUrlService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTest {

   @Autowired
   private ShortUrlService shortUrlService;

   @Autowired
   private ShortUrlEsService shortUrlEsService;

   @Autowired
   private ElasticsearchHelper elasticsearchHelper;

   @Autowired
   private CustomShortUrlEsService customShortUrlEsService;


   @Test
   public void testSaveAndReturnShortUrl(){
       ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().longUrl("https://www.baidu.com").urlName("百度").remark("百度一下，你就知道").build();
       String shortUrl = shortUrlService.saveAndReturnShortUrl(shortUrlDTO);
       Assert.assertNotNull(shortUrl);
       System.out.println(shortUrl);
   }

   @Test
   public void testSaveShortUrlEs(){
     ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().longUrl("https://www.baidu.com").urlName("百度").remark("百度一下，你就知道").build();
     ShortUrlVO shortUrlVO = shortUrlEsService.save(shortUrlDTO);
     Assert.assertNotNull(shortUrlVO);
     System.out.println(shortUrlVO);
   }


   @Test
   public void testCustomSaveShortUrlEsAndReturnId(){
     ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().id(20L).longUrl("https://music.wangyiyun.com/").urlName("网易云").remark("网易云音乐").build();
     String id = customShortUrlEsService.save(shortUrlDTO);
     Assert.assertNotNull(id);

   }

   @Test
   public void testCustomPageShortUrl(){
     PageQuery pageQuery = new PageQuery<>().setPageNo(5).setPageSize(5);
     ShortUrlDTO dto = new ShortUrlDTO();
//     dto.setUrlName("优酷");
//     dto.setRemark("视频");
//     pageQuery.setQueryParams(dto);

     PageResult<ShortUrlDTO> pageResult = customShortUrlEsService.pageShortUrl(pageQuery);

     System.out.println(pageResult);
   }


   @Test
   public void testGetLongUrl(){
       String shortUrl = "http://localhost:8080/w";
       String longUrl = shortUrlService.getLongUrlByShortUrl(shortUrl);
       System.out.println(longUrl);
   }


  @Test
  public void testCustomSaveShortUrlEs(){
    ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().longUrl("https://www.meituan.com").urlName("美团外卖").remark("美团外卖APP").build();
    boolean isSuccess = customShortUrlEsService.saveShortUrl(shortUrlDTO);
    Assert.assertTrue(isSuccess);
  }


   @Test
   public void testListCustomShortUrls(){
     ShortUrlDTO dto = new ShortUrlDTO();
     dto.setUrlName("美团");
   //  dto.setRemark("门户");
     List<ShortUrlDTO> shortUrlDTOS = customShortUrlEsService.listShortUrls(dto);

     System.out.println(shortUrlDTOS);
   }


   @Test
   public void testCustomGetShortUrl(){
     ShortUrlDTO shortUrlDTO = customShortUrlEsService.getShortUrlById(2L);
     Assert.assertNotNull(shortUrlDTO);
     System.out.println(shortUrlDTO);
   }


   @Test
   public void testDeleteCustomShortUrl(){
     boolean isSuccess = customShortUrlEsService.deleteShortUrlById(2L);
     Assert.assertTrue(isSuccess);
   }



    @Test
    public void testDeleteIndex(){
      boolean flag = elasticsearchHelper.deleteIndex(ElasticsearchConstant.SHORT_URL_INDEX);
      Assert.assertTrue(flag);
    }

    @Test
    public void testSaveShortUrl(){
      ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().longUrl("https://music.wangyiyun.com").urlName("网易云").remark("网易云音乐").build();
      ShortUrlDTO shortUrl = shortUrlService.saveShortUrl(shortUrlDTO);
      Assert.assertNotNull(shortUrl);
      System.out.println(shortUrl);
    }

  @Test
  public void testUpdateShortUrl(){
    ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().id(1L).longUrl("https://www.baidu.com").urlName("百度").remark("百度搜索引擎").build();
    ShortUrlDTO shortUrl = shortUrlService.saveShortUrl(shortUrlDTO);
    Assert.assertNotNull(shortUrl);
    System.out.println(shortUrl);
  }

  @Test
  public void testPage(){
    PageQuery pageQuery = new PageQuery<>().setPageNo(1).setPageSize(5);
    ShortUrlDTO dto = new ShortUrlDTO();
    dto.setUrlName("门户");
    // dto.setRemark("门户");
    pageQuery.setQueryParams(dto);

    PageResult<ShortUrlDTO> pageResult = shortUrlService.pageShortUrl(pageQuery);
    System.out.println(pageResult);



  }

  @Test
  public void testListShortUrl(){
    ShortUrlDTO dto = new ShortUrlDTO();
   // dto.setUrlName("百度");
    List<ShortUrlDTO> shortUrlDTOS = shortUrlService.listShortUrls(dto);

    System.out.println(shortUrlDTOS);
  }


  @Test
  public void testDeleteShortUrl(){
     boolean isDelete = shortUrlService.deleteShortUrlById(1L);
     Assert.assertTrue(isDelete);
  }


}

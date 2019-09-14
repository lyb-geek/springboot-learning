package com.github.lybgeek;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.shorturl.dto.ShortUrlDTO;
import com.github.lybgeek.shorturl.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTest {

   @Autowired
   private ShortUrlService shortUrlService;

   @Test
   public void testSaveAndReturnShortUrl(){
       ShortUrlDTO shortUrlDTO = ShortUrlDTO.builder().longUrl("http://www.baidu.com").urlName("百度").remark("百度一下，你就知道").build();
       String shortUrl = shortUrlService.saveAndReturnShortUrl(shortUrlDTO);
       Assert.assertNotNull(shortUrl);
       System.out.println(shortUrl);
   }

   @Test
   public void testGetLongUrl(){
       String shortUrl = "http://localhost:8080/w";
       String longUrl = shortUrlService.getLongUrlByShortUrl(shortUrl);
       System.out.println(longUrl);
   }


    @Test
    public void testPage(){
        PageQuery pageQuery = new PageQuery<>().setPageNo(1).setPageSize(5);
        ShortUrlDTO dto = new ShortUrlDTO();
        dto.setUrlName("百度");
        pageQuery.setQueryParams(dto);

        PageResult<ShortUrlDTO> pageResult = shortUrlService.pageShortUrl(pageQuery);

        if(pageResult != null){
            pageResult.getList().forEach(result -> System.out.println(result));
        }

    }


}

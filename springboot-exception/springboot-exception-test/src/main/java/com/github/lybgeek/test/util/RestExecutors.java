package com.github.lybgeek.test.util;


import com.github.lybgeek.exception.BizException;
import com.github.lybgeek.resp.model.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.concurrent.Callable;

@Slf4j
public class RestExecutors {

   public static <T> T sumbit(Callable<AjaxResult<T>> task){
       try {
           AjaxResult<T> ajaxResult = task.call();
           if(ajaxResult.getSuccess()){
               return ajaxResult.getData();
           }
           throw new BizException(ajaxResult.getCode(),ajaxResult.getMessage());
       } catch (BizException e) {
           throw e;
       }catch (Exception e){
           log.error("{}",e);
           throw new BizException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
       }

   }
}

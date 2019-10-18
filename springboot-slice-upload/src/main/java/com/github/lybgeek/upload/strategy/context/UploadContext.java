package com.github.lybgeek.upload.strategy.context;


import com.github.lybgeek.common.util.SpringContextHolder;
import com.github.lybgeek.upload.strategy.SliceUploadStrategy;
import com.github.lybgeek.upload.strategy.annotation.UploadMode;
import com.github.lybgeek.upload.strategy.enu.UploadModeEnum;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.CollectionUtils;
import org.reflections.Reflections;
import org.springframework.util.Assert;

public enum UploadContext {
   INSTANCE;

   private static final String PACKAGE_NAME = "com.github.lybgeek.upload.strategy.impl";

   private Map<UploadModeEnum,Class<SliceUploadStrategy>> uploadStrategyMap = new ConcurrentHashMap<>();


   public void init(){
     Reflections reflections = new Reflections(PACKAGE_NAME);
     Set<Class<?>> clzSet = reflections.getTypesAnnotatedWith(UploadMode.class);
     if(CollectionUtils.isNotEmpty(clzSet)){
       for (Class<?> clz : clzSet) {
         UploadMode uploadMode = clz.getAnnotation(UploadMode.class);
         uploadStrategyMap.put(uploadMode.mode(), (Class<SliceUploadStrategy>) clz);
       }
     }
   }

   public SliceUploadStrategy getInstance(UploadModeEnum mode){
     return this.getStrategyByType(mode);

   }


   private SliceUploadStrategy getStrategyByType(UploadModeEnum mode){
     Class<SliceUploadStrategy> clz = uploadStrategyMap.get(mode);
     Assert.notNull(clz,"mode:"+mode+"can not found class,please checked");
     return SpringContextHolder.getBean(clz);
   }

}

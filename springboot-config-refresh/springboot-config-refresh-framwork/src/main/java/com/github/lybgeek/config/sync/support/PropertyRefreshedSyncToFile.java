package com.github.lybgeek.config.sync.support;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.config.property.RefreshConfigProperty;
import com.github.lybgeek.config.sync.PropertyRefreshedSync;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
public class PropertyRefreshedSyncToFile implements PropertyRefreshedSync, InitializingBean {

     private final RefreshConfigProperty refreshConfigProperty;

     private final static String FILE_NAME = "properties.json";

     @Autowired
     private EnvironmentManager environmentManager;

     private File propertiesFile;

     private  Map<String,Object> propertiesMap = new ConcurrentHashMap<>();

    @Override
    public void execute(String name, Object value) {
        if(propertiesMap.containsKey(name)){
            Object oldValue = propertiesMap.get(name);
            if(!oldValue.equals(value)){
                propertiesMap.put(name,value);
            }
        }else{
            propertiesMap.put(name,value);
        }

        String propertiesJson = JSONUtil.toJsonStr(propertiesMap);
        FileUtil.writeString(propertiesJson, propertiesFile, "UTF-8");

    }



    @Override
    public void afterPropertiesSet() throws Exception {
        propertiesFile = getPropertiesFile();
        String propertiesJson = FileUtil.readString(propertiesFile, "UTF-8");
        if(JSONUtil.isJson(propertiesJson)){
            propertiesMap = JSONUtil.toBean(propertiesJson,Map.class);

            propertiesMap.forEach((k,v)->{
                environmentManager.setProperty(k,String.valueOf(v));
            });
        }

    }

    private File getPropertiesFile() throws IOException {
       String basePath = refreshConfigProperty.getStorePropertiesLocation();
       if(StringUtils.isEmpty(basePath)){
           basePath = getProjectPath();
       }
       String fileName = basePath + File.separator + FILE_NAME;
       if(log.isDebugEnabled()){
           log.debug("{}",fileName);
       }
       File file = new File(fileName);
       if(!file.exists()){
          file.createNewFile();
       }

       return file;

    }

    /**
     * 获取项目路径
     * @return
     */
    private static String getProjectPath() {
        return System.getProperty("user.dir");
    }
}

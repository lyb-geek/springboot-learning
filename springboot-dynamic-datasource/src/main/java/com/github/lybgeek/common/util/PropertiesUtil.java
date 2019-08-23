package com.github.lybgeek.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public enum PropertiesUtil {
    INSTANCE;
 

    private  Properties props;

    private PropertiesUtil() {
        initProps();
    }



    public void initProps(){
        String fileName = "druid.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }

    }
 

    //自定义俩个get方法，方便调用工具类读取properties文件的属性
    public String getProperty(String key){
        String value= props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            return null;
        }
        return value.trim();
    }
 
    public String getProperty(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)){
            value = defaultValue;
        }
        return value.trim();
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtil.INSTANCE.getProperty("author.name"));
    }
}


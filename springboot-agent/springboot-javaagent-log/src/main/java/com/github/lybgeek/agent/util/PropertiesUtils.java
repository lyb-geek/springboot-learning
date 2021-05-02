package com.github.lybgeek.agent.util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties(String filePath){
        if(!filePath.endsWith("properties")){
            throw new RuntimeException("文件格式不正确，仅支持.properties格式文件");
        }
        Properties properties = new Properties();
        try {
            InputStream InputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
            properties.load(InputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }


}

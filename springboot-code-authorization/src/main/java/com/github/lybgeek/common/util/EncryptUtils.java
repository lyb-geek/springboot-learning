package com.github.lybgeek.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class EncryptUtils {

    private static String secretKey = "test123456lyb-geek"+System.currentTimeMillis();

    private EncryptUtils(){}


    public static void encrypt(String classFileSrcPath,String classFileDestPath) {
        System.out.println(secretKey);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(classFileSrcPath);
            fos = new FileOutputStream(classFileDestPath);
            int len;
            String[] arrs = secretKey.split("lyb-geek");
            long key = Long.valueOf(arrs[1]);
            System.out.println("key:"+key);
            while((len = fis.read())!=-1){
              byte data = (byte)(len + key + secretKey.length());
              fos.write(data);
            }
        } catch (Exception e) {
           log.error("encrypt fail:"+e.getMessage(),e);
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}

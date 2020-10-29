package com.github.lybgeek.common.classloader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomClassLoader extends ClassLoader{

    /**
     * 授权码
     */
    private String secretKey;

    private String SECRETKEY_PREFIX = "lyb-geek";


    /**
     * class文件的根目录
     */
    private String classRootDir = "META-INF/services/";

    public CustomClassLoader(String secretKey) {
        this.secretKey = secretKey;
    }


    public String getClassRootDir() {
        return classRootDir;
    }

    public void setClassRootDir(String classRootDir) {
        this.classRootDir = classRootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Class<?> clz = findLoadedClass(name);
        //先查询有没有加载过这个类。如果已经加载，则直接返回加载好的类。如果没有，则加载新的类。
        if(clz != null){
            return clz;
        }else{
            ClassLoader parent = this.getParent();
            clz = getaClass(name, clz, parent);

            if(clz != null){
                return clz;
            }else{
                clz = getaClass(name);
            }

        }

        return clz;

    }

    private Class<?> getaClass(String name) throws ClassNotFoundException {
        Class<?> clz;
        byte[] classData = getClassData(name);
        if(classData == null){
            throw new ClassNotFoundException();
        }else{
            clz = defineClass(name, classData, 0,classData.length);
        }
        return clz;
    }

    private Class<?> getaClass(String name, Class<?> clz, ClassLoader parent) {
        try {
            //委派给父类加载
            clz = parent.loadClass(name);
        } catch (Exception e) {
           //log.warn("parent load class fail："+ e.getMessage(),e);
        }
        return clz;
    }

    private byte[] getClassData(String classname){
        if(StringUtils.isEmpty(secretKey) || !secretKey.contains(SECRETKEY_PREFIX) || secretKey.split(SECRETKEY_PREFIX).length != 2){
            throw new RuntimeException("secretKey is illegal");
        }
        String path = CustomClassLoader.class.getClassLoader().getResource("META-INF/services/").getPath() +"/"+ classname+".lyb";
        InputStream is = null;
        ByteArrayOutputStream bas = null;
        try{
            is  = new FileInputStream(path);
            bas = new ByteArrayOutputStream();
            int len;
            //解密
            String[] arrs = secretKey.split(SECRETKEY_PREFIX);
            long key = Long.valueOf(arrs[1]);
          //  System.out.println("key:"+key);
            while((len = is.read())!=-1){
                byte data = (byte)(len - key - secretKey.length());
                bas.write(data);
            }
            return bas.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally{
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                log.error("encrypt fail:"+e.getMessage(),e);
            }
            try {
                if(bas!=null){
                    bas.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

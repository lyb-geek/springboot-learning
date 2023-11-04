package com.github.lybgeek.groovy.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ServiceLoader;

@Slf4j
public final class DirectoryUtil {

    private DirectoryUtil(){}

    public static File getDirectory(String sPath){
         File file = null;
        try {
            file = getDirectoryFromClassPath(sPath);
            if(ObjectUtils.isEmpty(file)){
                return loadFromFilePath(sPath);
            }
        } catch (IOException e) {
           log.error(">>>>>>>>>>>>>>>>>>> GetDirectory fail",e);
        }
        return file;
    }


    public static File loadFromFilePath(String path){
       File file = new File(path);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file.getAbsolutePath() + " is not a valid directory");
        }
        return file;
    }


    /**
     * get classloader from thread context if no classloader found in thread
     * context return the classloader which has loaded this class
     *
     * @return classloader
     */
    public static ClassLoader getCurrentClassLoader() {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = DirectoryUtil.class.getClassLoader();
        }
        return classLoader;
    }



    public static File getDirectoryFromClassPath(String path) throws IOException {
        try {
            Enumeration<URL> resources = getCurrentClassLoader().getResources(path);
            File file = null;
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                UrlResource resource = new UrlResource(url);
                file = resource.getFile();
            }
            return file;
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load file from location [" +
                    path + "]", e);
        }

    }


}

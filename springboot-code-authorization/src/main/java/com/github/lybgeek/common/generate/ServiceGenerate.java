package com.github.lybgeek.common.generate;

import com.github.lybgeek.common.util.EncryptUtils;

public class ServiceGenerate {

    public static void main(String[] args) {
        String classFileSrcPath = classFileSrcPath("UserServiceImpl");
        System.out.println("classFileSrcPath:--->"+classFileSrcPath);
        String classFileDestDir = ServiceGenerate.class.getClassLoader().getResource("META-INF/services/").getPath();
        System.out.println("classFileDestDir:--->"+classFileDestDir);
        String classFileDestPath = classFileDestDir + "com.github.lybgeek.user.service.impl.UserServiceImpl.lyb";
        EncryptUtils.encrypt(classFileSrcPath,classFileDestPath);
    }


    private static String classFileSrcPath(String className) {
        String basePath = ServiceGenerate.class.getResource("").getPath();
        String projectPath = basePath.substring(0, basePath.indexOf("/target"));
        String classFileSrcPath = projectPath + "/"+ className + ".class";
        return classFileSrcPath;
    }

}

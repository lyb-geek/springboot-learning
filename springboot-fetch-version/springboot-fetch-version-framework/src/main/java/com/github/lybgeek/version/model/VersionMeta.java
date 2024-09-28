package com.github.lybgeek.version.model;

/**
 * @description:
 * @author: Linyb
 * @date : 2024/9/27 16:02
 **/
public class VersionMeta {

    private String libraryClassName;
    private String libraryClassPackage;
    private String libraryClassVersion;

    public VersionMeta() {
    }

    public VersionMeta(String libraryClassVersion, String libraryClassName, String libraryClassPackage) {
        this.libraryClassVersion = libraryClassVersion;
        this.libraryClassName = libraryClassName;
        this.libraryClassPackage = libraryClassPackage;

    }

    public String getLibraryClassName() {
        return libraryClassName;
    }

    public void setLibraryClassName(String libraryClassName) {
        this.libraryClassName = libraryClassName;
    }

    public String getLibraryClassPackage() {
        return libraryClassPackage;
    }

    public void setLibraryClassPackage(String libraryClassPackage) {
        this.libraryClassPackage = libraryClassPackage;
    }

    public String getLibraryClassVersion() {
        return libraryClassVersion;
    }

    public void setLibraryClassVersion(String libraryClassVersion) {
        this.libraryClassVersion = libraryClassVersion;
    }
}

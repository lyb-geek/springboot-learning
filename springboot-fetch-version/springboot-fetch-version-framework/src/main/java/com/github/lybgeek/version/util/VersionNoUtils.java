package com.github.lybgeek.version.util;


import com.github.lybgeek.version.VersionNoFetcher;
import com.github.lybgeek.version.http.HttpUtils;
import com.github.lybgeek.version.model.VersionMeta;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.lybgeek.version.util.StringUtils.EXTENSION_SEPARATOR;

public final class VersionNoUtils {
    private static final String APP_ID_KEY = "appid";
    private static final String VERSION_NO_KEY = "versionNo";


    private VersionNoUtils(){}

    public static void reportVersion2RemoteSvc(String url,String appId,ClassLoader classLoader,String libraryClassNames)  {
        if(!StringUtils.hasText(libraryClassNames) || !StringUtils.hasText(url)){
            return;
        }
        Map<String,Object> params = new HashMap<>();
        params.put(APP_ID_KEY,appId);
        if(libraryClassNames.contains(EXTENSION_SEPARATOR)){
            String[] libClassNames = libraryClassNames.split(EXTENSION_SEPARATOR);
            for(String libClassName : libClassNames){
                addVersionForClass(classLoader, libClassName,params);
            }
        }else{
            addVersionForClass(classLoader, libraryClassNames,params);
        }

        sendVersion2RemoteSvc(url, params);

    }


    public static void reportVersion2RemoteSvc(String url,String appId,String versionNos)  {
        Map<String,Object> params = new HashMap<>();
        params.put(APP_ID_KEY,appId);
        params.put(VERSION_NO_KEY,versionNos);


        sendVersion2RemoteSvc(url, params);

    }

    private static void sendVersion2RemoteSvc(String url, Map<String, Object> params) {
        if(!params.isEmpty()){
            try {
                String jsonContent = JsonConverter.mapToJson(params);
                HttpUtils.getInstance().postJson(url, jsonContent);
            } catch (IOException e) {

            }
        }
    }

    private static void addVersionForClass(ClassLoader classLoader, String libClassName,Map<String, Object> params) {
        VersionMeta versionMeta = VersionNoFetcher.getVersion(classLoader,libClassName);
        if(StringUtils.hasText(versionMeta.getLibraryClassVersion()) && StringUtils.hasText(versionMeta.getLibraryClassPackage())){
            params.put(versionMeta.getLibraryClassPackage(),versionMeta.getLibraryClassVersion());
        }
    }

    public static void main(String[] args) {

        reportVersion2RemoteSvc("http://localhost:8080/version/report","10000",Thread.currentThread().getContextClassLoader(), "com.github.lybgeek.common.util.StringUtils");
    }

}

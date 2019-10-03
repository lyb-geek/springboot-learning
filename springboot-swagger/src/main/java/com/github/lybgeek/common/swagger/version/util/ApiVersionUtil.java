package com.github.lybgeek.common.swagger.version.util;

import com.github.lybgeek.common.swagger.version.annotation.ApiVersion;
import org.apache.commons.lang3.StringUtils;

public enum ApiVersionUtil {

  INSTANCE;

  public static final String SEPARATION = ".";

  public static final String URL_SEPARATION = "/";

  public static final String DEFAULT_API_VERSION = "1.0.0";


  public int compareTo(String curentApiVersion , String otherApiVersion){

     String[] curVersionArr = StringUtils.split(curentApiVersion,SEPARATION);
     String[] otherVersionArr = StringUtils.split(otherApiVersion,SEPARATION);

     Integer curMajorVersion = Integer.valueOf(curVersionArr[0]);
     Integer otherMajorVersion = Integer.valueOf(otherVersionArr[0]);

     if(curMajorVersion.equals(otherMajorVersion)){
       //主版本号相等的情况下，继续匹配次版本号
       Integer curMinorVersion = Integer.valueOf(curVersionArr[1]);
       Integer otherMinorVersion = Integer.valueOf(otherVersionArr[1]);
       //次版本号相等的情况下，继续匹配修订版本号
       if(curMinorVersion.equals(otherMinorVersion)){
         Integer curRevisionVersion = Integer.valueOf(curVersionArr[2]);
         Integer otherRevisionVersion = Integer.valueOf(otherVersionArr[2]);
         return curRevisionVersion - otherRevisionVersion;
       }

       return curMinorVersion - otherMinorVersion;
     }

     return curMajorVersion - otherMajorVersion;

  }


  public String getApiVersion(ApiVersion apiVersion){
    Integer majorVersion = apiVersion.majorVersion();
    Integer minorVersion = apiVersion.minorVersion();
    Integer revisionVersion = apiVersion.revisionVersion();
    StringBuilder apiVersionStr = new StringBuilder();
    apiVersionStr.append(majorVersion).append(SEPARATION)
        .append(minorVersion).append(SEPARATION).append(revisionVersion);

    return apiVersionStr.toString();

  }

  public static String withoutHeadAndTailDiagonal(String apiVersion) {

    int start = 0;
    int end = 0;
    boolean existHeadDiagonal = apiVersion.startsWith(URL_SEPARATION);
    boolean existTailDiagonal = apiVersion.endsWith(URL_SEPARATION);
    if (existHeadDiagonal && existTailDiagonal) {
      start = StringUtils.indexOf(apiVersion, URL_SEPARATION, 0) + 1;
      end = StringUtils.lastIndexOf(apiVersion, URL_SEPARATION);
      return StringUtils.substring(apiVersion, start, end);
    } else if (existHeadDiagonal && !existTailDiagonal) {
      start = StringUtils.indexOf(apiVersion, URL_SEPARATION, 0) + 1;
      return StringUtils.substring(apiVersion, start);
    } else if (!existHeadDiagonal && existTailDiagonal) {
      end = StringUtils.lastIndexOf(apiVersion, URL_SEPARATION);
      return StringUtils.substring(apiVersion, 0, end);
    }
    return apiVersion;
  }

}

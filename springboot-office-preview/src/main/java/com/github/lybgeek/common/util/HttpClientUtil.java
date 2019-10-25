package com.github.lybgeek.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@Slf4j
public enum HttpClientUtil {
  INSTANCE;

  public static final String SLASH_ONE = "/";
  public static final String SLASH_TWO = "\\";

  RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();

  public String post(String url, Map<String,Object> params,String fileName){
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    try {

      HttpPost httpPost = new HttpPost(url);
      httpPost.setConfig(requestConfig);

      MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
      multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
      multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

      if(Objects.nonNull(params) && !params.isEmpty()){
        params.forEach((key,value)->{
            if(value instanceof File){
              File file = (File) value;
              multipartEntityBuilder.addPart(key, new FileBody(file));

            }else if(value instanceof String){
              String str = (String) value;
              multipartEntityBuilder.addPart(key, new StringBody(str,ContentType.TEXT_PLAIN));
            }else if(value instanceof byte[]){
              byte[] bytes = (byte[]) value;
              String name = StringUtils.isNotBlank(fileName) ? fileName : key;
              multipartEntityBuilder.addPart(key, new ByteArrayBody(bytes,ContentType.DEFAULT_BINARY,name));

            }else{
              throw new IllegalArgumentException(key+"的类型是"+value.getClass()+"（允许的参数类型为File 或者 String）。");
            }

        });
      }

      HttpEntity httpEntity = multipartEntityBuilder.build();
      httpPost.setEntity(httpEntity);
      response = httpClient.execute(httpPost);

      HttpEntity resEntity = response.getEntity();
      if (resEntity != null) {
        String result = EntityUtils.toString(resEntity);
        EntityUtils.consume(resEntity);
        System.out.println("success");
        return result;
      }

    } catch (IOException e) {
      log.error("upload error:"+e.getMessage(),e);
    } finally {
       close(httpClient, response);
    }
    return null;

  }


  public void download(String url,String remoteFileName,Map<String,Objects> params,String localFilePath){
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    HttpGet httpGet = new HttpGet(url);
    httpGet.setConfig(requestConfig);

    httpGet.addHeader("fileName", remoteFileName);
    if(Objects.nonNull(params) && !params.isEmpty()){
      params.forEach((key,value)->httpGet.addHeader(key,value.toString()));
    }

    try {
      response = httpClient.execute(httpGet);

      HttpEntity resEntity = response.getEntity();

      if(Objects.nonNull(resEntity)){
         long len = resEntity.getContentLength();
         if(len <= 0){
           log.warn("remoteFileName：{} not found",remoteFileName);
           return;
         }

         File localStoreDir = new File(localFilePath);
         if(!localStoreDir.exists()){
           localStoreDir.mkdirs();
         }

         File localStoreFile = new File(localFilePath+SLASH_ONE+remoteFileName);
         if(!localStoreFile.exists()){
           localStoreFile.createNewFile();
         }

        FileUtils.copyToFile(resEntity.getContent(),localStoreFile);
        System.out.println("-------remoteFileName:"+remoteFileName+"--------下载成功");
      }
    } catch (IOException e) {
       log.error("download error:"+e.getMessage(),e);
    } finally {
      close(httpClient, response);
    }
  }

  private void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {

    try {

      if(Objects.nonNull(response)){
        response.close();
      }

      if(Objects.nonNull(httpClient)){
        httpClient.close();
      }

    } catch (IOException e) {
      log.error(e.getMessage(),e);
    }
  }

  public static void main(String[] args) {
    Map<String,Object> params = new HashMap<>();
    String url = "http://localhost:8080/preview/convertFile";
    String localPath = "D:/desktop/uploads/libreoffice命令.txt";
    File file = new File(localPath);
    params.put("file",file);

    HttpClientUtil.INSTANCE.post(url,params,null);
  }
}

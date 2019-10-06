package com.github.lybgeek.httpclient.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 参考 https://blog.csdn.net/zy12306/article/details/88554663
 */
@Component
@Slf4j
public class HttpClientUtil {

    @Autowired
    private CloseableHttpClient httpClient;

    public  String doGet(String url, Map<String, String> param,Map<String,String> headers) {

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            builder.setCharset(Charset.forName("utf-8"));
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if(headers!= null && !headers.isEmpty()){
                headers.forEach((name,value)->httpGet.setHeader(name,value));
            }

            // 执行请求
            response = httpClient.execute(httpGet);
           return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                close(response);
                httpClient.close();
            } catch (IOException e) {
               log.error("doGet url:"+url+" fail,cause :"+e.getMessage(),e);
            }
        }
        return resultString;
    }

    public  String doGet(String url) {
        return doGet(url, null,null);
    }

    public  String doPost(String url, Map<String, String> param,Map<String,String> headers) {
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,Charset.forName("UTF-8"));
                httpPost.setEntity(entity);
            }

            if(headers!= null && !headers.isEmpty()){
                headers.forEach((name,value)->httpPost.setHeader(name,value));
            }

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("doPost url:"+url+" fail,cause :"+e.getMessage(),e);
        } finally {
            close(response);
        }

        return resultString;
    }

    private void close(CloseableHttpResponse response) {
        try {
            if(response != null){
                response.close();
            }
        } catch (IOException e) {
         log.error(e.getMessage(),e);
        }
    }

    public  String doPost(String url) {
        return doPost(url, null,null);
    }
    
    public  String doPostJson(String url, String json) {
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("doJson url:"+url+" fail,cause :"+e.getMessage(),e);
        } finally {
            close(response);
        }

        return resultString;
    }
}
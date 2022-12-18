package com.github.lybgeek.http.demo;


import com.github.lybgeek.http.HttpTemplateComposite;
import com.github.lybgeek.http.common.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class HttpDemoApplication implements ApplicationRunner {


    @Autowired
    private HttpTemplateComposite httpTemplateComposite;

    public static void main(String[] args) {
        SpringApplication.run(HttpDemoApplication.class);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
       testHttpReq();


    }

    private static String getProjectPath() {
        String basePath = HttpDemoApplication.class.getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }

    private void testHttpReq() {
        String url = "http://localhost:8080/echo";
        Map<String,String> headerMap = new HashMap<>();
        headerMap.put("clientId",httpTemplateComposite.getHttpProperties().getClientId());
        headerMap.put("secret",httpTemplateComposite.getHttpProperties().getSecret());

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("content","世界");

        Object result = httpTemplateComposite.execute(url, HttpMethod.GET, headerMap, paramMap);

        System.out.println(result);
    }


}

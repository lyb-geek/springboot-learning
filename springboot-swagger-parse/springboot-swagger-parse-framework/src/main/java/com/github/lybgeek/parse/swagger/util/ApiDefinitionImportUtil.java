package com.github.lybgeek.parse.swagger.util;


import cn.hutool.core.collection.CollectionUtil;
import com.github.lybgeek.parse.swagger.api.definition.ApiDefinitionDetail;
import com.github.lybgeek.parse.swagger.api.http.config.HTTPConfig;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.binary.BinaryBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormDataBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.WWWFormBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.json.JsonBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.none.NoneBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.raw.RawBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.xml.XmlBody;
import com.github.lybgeek.parse.swagger.api.http.request.HttpRequest;
import com.github.lybgeek.parse.swagger.api.http.request.RequestBody;
import com.github.lybgeek.parse.swagger.auth.NoAuth;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ApiDefinitionImportUtil {
    private static final String FILE_JMX = "jmx";
    private static final String FILE_HAR = "har";
    private static final String FILE_JSON = "json";

    public static HttpRequest buildHttpRequest(String name, String path, String method) {
        HttpRequest request = new HttpRequest();
        request.setName(name);
        // 路径去掉域名/IP 地址，保留方法名称及参数
        request.setPath(formatPath(path));
        request.setMethod(method);
        request.setHeaders(new ArrayList<>());
        request.setQuery(new ArrayList<>());
        request.setRest(new ArrayList<>());
        request.setBody(new RequestBody());
        HTTPConfig httpConfig = new HTTPConfig();
        httpConfig.setConnectTimeout(60000L);
        httpConfig.setResponseTimeout(60000L);
        request.setOtherConfig(httpConfig);
        request.setAuthConfig(new NoAuth());
        //        assertionConfig
        RequestBody body = new RequestBody();
        body.setBinaryBody(new BinaryBody());
        body.setFormDataBody(new FormDataBody());
        body.setXmlBody(new XmlBody());
        body.setRawBody(new RawBody());
        body.setNoneBody(new NoneBody());
        body.setJsonBody(new JsonBody());
        body.setWwwFormBody(new WWWFormBody());
        body.setNoneBody(new NoneBody());
        body.setBodyType(RequestBody.BodyType.NONE.name());
        request.setBody(body);



        return request;
    }

    public static String formatPath(String url) {
        try {
            URI urlObject = new URI(url);
            return StringUtils.isBlank(urlObject.getPath()) ? url : urlObject.getPath();
        } catch (Exception ex) {
            //只需要返回？前的路径
            return url.split("\\?")[0];
        }
    }



    public static List<ApiDefinitionDetail> apiRename(List<ApiDefinitionDetail> caseList) {
        List<ApiDefinitionDetail> returnList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(caseList)) {
            List<String> caseNameList = new ArrayList<>();
            for (ApiDefinitionDetail apiCase : caseList) {
                String uniqueName = getUniqueName(apiCase.getName(), caseNameList);
                apiCase.setName(uniqueName);
                caseNameList.add(uniqueName);
                returnList.add(apiCase);
            }
        }
        return returnList;
    }



    public static String getUniqueName(String originalName, List<String> existenceNameList) {
        String returnName = originalName;
        if (existenceNameList.contains(returnName)) {
            if (originalName.length() > 250) {
                originalName = originalName.trim().substring(0, 250);
            }
            int index = 1;
            do {
                returnName = originalName + "-" + index;
                index++;
            } while (existenceNameList.contains(returnName));
        }
        return returnName;
    }
}

package com.github.lybgeek.parse.test;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONUtil;
import com.github.lybgeek.parse.swagger.api.ApiDefinitionImportParserResult;
import com.github.lybgeek.parse.swagger.api.http.request.RequestBody;
import com.github.lybgeek.parse.swagger.constant.PropertyConstant;
import com.github.lybgeek.parse.swagger.dto.ImportRequest;
import com.github.lybgeek.parse.swagger.export.dto.ApiDefinitionWithBlob;
import com.github.lybgeek.parse.swagger.export.dto.SwaggerApiDefinitionExportResponse;
import com.github.lybgeek.parse.swagger.export.dto.SwaggerInfo;
import com.github.lybgeek.parse.swagger.export.service.ExportParser;
import com.github.lybgeek.parse.swagger.export.service.impl.SwaggerApiDefinitionExportParser;
import com.github.lybgeek.parse.swagger.service.ApiDefinitionImportParser;
import com.github.lybgeek.parse.swagger.service.impl.SwaggerApiDefinitionImportParser;
import com.github.lybgeek.parse.swagger.util.json.JsonUtil;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.InputStream;
import java.util.*;

public class SwaggerParseTest {


    @Test
    public void testSwaggerWithoutToken() throws Exception{
//        String url = ResourceUtils.getFile("classpath:swagger/swagger.yml").toURI().toString();
        String url = "http://localhost:8080/v2/api-docs";
        System.out.println(url);
        SwaggerParseResult swaggerParseResult = new OpenAPIParser().readLocation(url, null, null);
        OpenAPI openAPI = swaggerParseResult.getOpenAPI();
        if(swaggerParseResult.getMessages() != null){
            swaggerParseResult.getMessages().forEach(System.out::println);
        }

        if(openAPI != null){
            System.out.println(JSONUtil.toJsonStr(openAPI));
        }



    }

    @Test
    public void testSwaggerToken(){
        // 构建授权值
        AuthorizationValue mySpecialHeader = new AuthorizationValue()
                .keyName("token")  // 要传递的授权名称
                .value("123456")        // 授权的值
                .type("header");

        SwaggerParseResult swaggerParseResult = new OpenAPIParser().readLocation("http://localhost:8080/v2/api-docs", Arrays.asList(mySpecialHeader), null);
        OpenAPI openAPI = swaggerParseResult.getOpenAPI();
        if(swaggerParseResult.getMessages() != null){
            swaggerParseResult.getMessages().forEach(System.out::println);
        }

        if(openAPI != null){
            System.out.println(JSONUtil.toJsonStr(openAPI));
        }


    }

    @Test
    public void testImportSwaggerInfoByUrl() throws Exception {
        ApiDefinitionImportParser<ApiDefinitionImportParserResult> apiDefinitionImportParser = new SwaggerApiDefinitionImportParser();
        ImportRequest importRequest = new ImportRequest();
        importRequest.setSwaggerUrl("http://localhost:8080/v2/api-docs");
        System.out.println(JSONUtil.toJsonStr(apiDefinitionImportParser.parse(null, importRequest)));
    }

    @Test
    public void testImportSwaggerInfoByYamlFile() throws Exception {
        String url = ResourceUtils.getFile("classpath:swagger/swagger.yml").toURI().toString();
        System.out.println(url);
        importSwaggerInfoByFile(url);
    }

    @Test
    public void testImportSwaggerInfoByJsonFile() throws Exception {
        String url = ResourceUtils.getFile("classpath:swagger/swagger.json").toURI().toString();
        System.out.println(url);
        importSwaggerInfoByFile(url);
    }

    private void importSwaggerInfoByFile(String filePath) throws Exception {
        ApiDefinitionImportParser<ApiDefinitionImportParserResult> apiDefinitionImportParser = new SwaggerApiDefinitionImportParser();
        ImportRequest importRequest = new ImportRequest();
        importRequest.setSwaggerFilePath(filePath);
        System.out.println(JSONUtil.toJsonStr(apiDefinitionImportParser.parse(null, importRequest)));
    }



    @Test
    public void testImportSwaggerInfoByJsonInputStream() throws Exception{
        importSwaggerInfoByFileInputStream("swagger/swagger.json");

    }

    @Test
    public void testImportSwaggerInfoByYamlInputStream() throws Exception{
        importSwaggerInfoByFileInputStream("swagger/swagger.yml");

    }

    private void importSwaggerInfoByFileInputStream(String path) throws Exception {
        ApiDefinitionImportParser<ApiDefinitionImportParserResult> apiDefinitionImportParser = new SwaggerApiDefinitionImportParser();
        InputStream inputStream = ResourceUtils.getFile("classpath:" + path).toURI().toURL().openStream();
        System.out.println(JSONUtil.toJsonStr(apiDefinitionImportParser.parse(inputStream, new ImportRequest())));
    }


    @Test
    public void testExport() throws Exception{
        ExportParser<SwaggerApiDefinitionExportResponse> exportParser = new SwaggerApiDefinitionExportParser();
        List<ApiDefinitionWithBlob> list = new ArrayList<>();
        ApiDefinitionWithBlob apiDefinitionWithBlob = getApiDefinitionWithBlob();

        Map<String,String> moduleMap = new HashMap<>();
        moduleMap.put(apiDefinitionWithBlob.getModuleId(),"导出管理");


        fillRequest(apiDefinitionWithBlob);
        fillResponse(apiDefinitionWithBlob);


        list.add(apiDefinitionWithBlob);

        SwaggerInfo swaggerInfo = getSwaggerInfo();

        SwaggerApiDefinitionExportResponse swaggerApiDefinitionExportResponse = exportParser.parse(list, swaggerInfo, moduleMap);
        System.out.println(JsonUtil.toJSONString(swaggerApiDefinitionExportResponse));
        System.out.println(getProjectPath());
        FileUtil.writeString(JsonUtil.toJSONString(swaggerApiDefinitionExportResponse), getProjectPath() + "/swagger-export.json", CharsetUtil.UTF_8);

    }

    private static SwaggerInfo getSwaggerInfo() {
        //info
        SwaggerInfo swaggerInfo = new SwaggerInfo();
        swaggerInfo.setVersion("3.x");
        swaggerInfo.setTitle("swagger.3.0");
        swaggerInfo.setDescription("LYB-GEEK SWAGGER RESTful APIs");
        swaggerInfo.setTermsOfService("LYB-GEEK");
        return swaggerInfo;
    }

    /**
     * 获取项目路径
     * @return
     */
    private static String getProjectPath() {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        return basePath.substring(0, basePath.indexOf("/target"));
    }

    private static ApiDefinitionWithBlob getApiDefinitionWithBlob() {
        ApiDefinitionWithBlob apiDefinitionWithBlob = new ApiDefinitionWithBlob();
        apiDefinitionWithBlob.setDescription("export");
        apiDefinitionWithBlob.setName("export");
        apiDefinitionWithBlob.setProtocol("http");
        apiDefinitionWithBlob.setMethod("get");
        apiDefinitionWithBlob.setPath("/export");
        apiDefinitionWithBlob.setModuleId("export");
        return apiDefinitionWithBlob;
    }

    private static void fillResponse(ApiDefinitionWithBlob apiDefinitionWithBlob) {
        Map<String,Object> responseJsonBody = new HashMap<>();

        Map<String,Object> responseJson = new HashMap<>();
        Map<String,Object> jsonValue = new HashMap<>();
        jsonValue.put("message","操作成功");
        jsonValue.put("success","true");
        responseJson.put("jsonValue",JsonUtil.toJSONString(jsonValue));


        Map<String,Object> responseJsonScheme = new HashMap<>();
        responseJsonScheme.put(PropertyConstant.TYPE, PropertyConstant.OBJECT);
        responseJson.put("jsonSchema",JsonUtil.toJSONString(responseJsonScheme));

        responseJsonBody.put(PropertyConstant.BODY_TYPE, RequestBody.BodyType.JSON.name());
        responseJsonBody.put("jsonBody",responseJson);


        Map<String,Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("value", "OK");
        response.put("body",responseJsonBody);
        apiDefinitionWithBlob.setResponse(response);
    }

    private static void fillRequest(ApiDefinitionWithBlob apiDefinitionWithBlob) {

        Map<String,Object> requestJsonBody = new HashMap<>();

        Map<String,Object> requestJson = new HashMap<>();
        Map<String,Object> jsonValue = new HashMap<>();
        jsonValue.put("name","lybgeek");
        jsonValue.put("age",18);
        requestJson.put("jsonValue",JsonUtil.toJSONString(jsonValue));


        Map<String,Object> requestJsonScheme = new HashMap<>();
        requestJsonScheme.put(PropertyConstant.TYPE, PropertyConstant.OBJECT);
        requestJson.put("jsonSchema",JsonUtil.toJSONString(requestJsonScheme));

        requestJsonBody.put(PropertyConstant.BODY_TYPE, RequestBody.BodyType.JSON.name());
        requestJsonBody.put("jsonBody",requestJson);


        Map<String,Object> request = new HashMap<>();
        request.put("body",requestJsonBody);


        apiDefinitionWithBlob.setRequest(request);
    }





}

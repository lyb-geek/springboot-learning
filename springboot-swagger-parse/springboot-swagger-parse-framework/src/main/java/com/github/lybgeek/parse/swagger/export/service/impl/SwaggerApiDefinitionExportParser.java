package com.github.lybgeek.parse.swagger.export.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.lybgeek.parse.swagger.api.http.request.RequestBody;
import com.github.lybgeek.parse.swagger.constant.MediaType;
import com.github.lybgeek.parse.swagger.constant.ModuleConstants;
import com.github.lybgeek.parse.swagger.constant.PropertyConstant;
import com.github.lybgeek.parse.swagger.export.dto.*;
import com.github.lybgeek.parse.swagger.export.service.ExportParser;
import com.github.lybgeek.parse.swagger.util.json.JsonUtil;
import com.github.lybgeek.parse.swagger.util.xml.XMLUtil;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SwaggerApiDefinitionExportParser implements ExportParser<SwaggerApiDefinitionExportResponse> {


    @Override
    public SwaggerApiDefinitionExportResponse parse(List<ApiDefinitionWithBlob> list, SwaggerInfo swaggerInfo, Map<String, String> moduleMap) throws Exception {
        SwaggerApiDefinitionExportResponse response = new SwaggerApiDefinitionExportResponse();
        //openapi
        response.setOpenapi(swaggerInfo.getSwaggerVersion());

        response.setInfo(swaggerInfo);
        //servers
        response.setServers(new ArrayList<>());
        //tags
        response.setTags(new ArrayList<>());

        response.setComponents(JsonUtil.createObj());
        ObjectNode externalDocs = JsonUtil.createObj();
        externalDocs.put("description", "");
        externalDocs.put("url", "");
        response.setExternalDocs(externalDocs);

        //path
        JSONObject paths = new JSONObject();
        JSONObject components = new JSONObject();
        List<JSONObject> schemas = new LinkedList<>();
        for (ApiDefinitionWithBlob apiDefinition : list) {
            if (apiDefinition.getPath() == null) {
                continue;
            }
            SwaggerApiInfo swaggerApiInfo = new SwaggerApiInfo();
            swaggerApiInfo.setSummary(apiDefinition.getName());
            String moduleName = "";
            if (StringUtils.equals(apiDefinition.getModuleId(), ModuleConstants.DEFAULT_NODE_ID)) {
                moduleName = "api unplanned request";
            } else {
                moduleName = moduleMap.get(apiDefinition.getModuleId());
            }
            swaggerApiInfo.setTags(Collections.singletonList(moduleName));
            //请求体
            JSONObject requestObject = null;
            if(MapUtil.isNotEmpty(apiDefinition.getRequest())){
                requestObject = new JSONObject(apiDefinition.getRequest());
                JSONObject requestBody = buildRequestBody(requestObject, schemas);
                swaggerApiInfo.setRequestBody(JsonUtil.parseObjectNode(requestBody.toString()));
            }


            //  设置响应体
            JSONArray responseObject = new JSONArray();

            if(Objects.nonNull(apiDefinition.getResponse())){
                List<Map<String,Object>> responseList = new ArrayList<>();
                responseList.add(apiDefinition.getResponse());
                responseObject = new JSONArray(responseList);

            }
            JSONObject jsonObject = buildResponseBody(responseObject, schemas);
            swaggerApiInfo.setResponses(JsonUtil.parseObjectNode(jsonObject.toString()));




            //  设置请求参数列表
            if(requestObject != null){
                List<JSONObject> paramsList = buildParameters(requestObject);
                List<JsonNode> nodes = new LinkedList<>();
                paramsList.forEach(item -> {
                    nodes.add(JsonUtil.parseObjectNode(item.toString()));
                });
                swaggerApiInfo.setParameters(nodes);
            }

            swaggerApiInfo.setDescription(apiDefinition.getDescription());
            JSONObject methodDetail = JsonUtil.parseObj(JsonUtil.toJSONString(swaggerApiInfo));
            if (paths.optJSONObject(apiDefinition.getPath()) == null) {
                paths.put(apiDefinition.getPath(), new JSONObject());
            }   //  一个路径下有多个发方法，如post，get，因此是一个 JSONObject 类型
            paths.optJSONObject(apiDefinition.getPath()).put(apiDefinition.getMethod().toLowerCase(), methodDetail);

        }
        response.setPaths(JsonUtil.parseObjectNode(paths.toString()));
        if (CollectionUtil.isNotEmpty(schemas)) {
            components.put("schemas", schemas.get(0));
        }
        response.setComponents(JsonUtil.parseObjectNode(components.toString()));

        return response;
    }


    private List<JSONObject> buildParameters(JSONObject request) {
        List<JSONObject> paramsList = new ArrayList<>();
        Hashtable<String, String> typeMap = new Hashtable<String, String>() {{
            put("headers", "header");
            put("rest", "path");
            put("query", "query");
        }};
        Set<String> typeKeys = typeMap.keySet();
        for (String type : typeKeys) {
            JSONArray params = request.optJSONArray(type);  //  获得请求参数列表
            if (params != null) {
                for (int i = 0; i < params.length(); ++i) {
                    JSONObject param = params.optJSONObject(i); //  对于每个参数:
                    if (StringUtils.isEmpty(param.optString("key"))) {
                        continue;
                    }   //  否则无参数的情况，可能多出一行空行
                    SwaggerParams swaggerParam = new SwaggerParams();
                    swaggerParam.setIn(typeMap.get(type));  //  利用 map，根据 request 的 key 设置对应的参数类型
                    swaggerParam.setDescription(param.optString("description"));
                    swaggerParam.setName(param.optString("key"));
                    swaggerParam.setEnable(param.optBoolean(PropertyConstant.ENABLE));
                    swaggerParam.setExample(param.optString("value"));
                    JSONObject schema = new JSONObject();
                    schema.put(PropertyConstant.TYPE, PropertyConstant.STRING);
                    swaggerParam.setSchema(JsonUtil.parseObjectNode(schema.toString()));
                    JSONObject schemaObject = JsonUtil.parseObj(JsonUtil.toJSONString(swaggerParam));
                    if (StringUtils.isNotBlank(param.optString("maxLength"))) {
                        schemaObject.put("maxLength", param.optInt("maxLength"));
                    }
                    if (StringUtils.isNotBlank(param.optString("minLength"))) {
                        schemaObject.put("minLength", param.optInt("minLength"));
                    }
                    paramsList.add(schemaObject);
                }
            }
        }
        return paramsList;
    }

    private JSONObject buildResponseBody(JSONArray response, List<JSONObject> schemas) {
        if (response.isEmpty()) {
            return new JSONObject();
        }
        JSONObject responseBody = new JSONObject();
        for (int i = 0; i < response.length(); i++) {
            JSONObject responseJSONObject = response.getJSONObject(i);
            JSONObject headers = new JSONObject();
            JSONArray headValueList = responseJSONObject.optJSONArray("headers");
            if (headValueList != null) {
                for (Object item : headValueList) {
                    if (item instanceof JSONObject && ((JSONObject) item).optString("key") != null) {
                        JSONObject head = new JSONObject(), headSchema = new JSONObject();
                        head.put("description", ((JSONObject) item).optString("description"));
                        head.put("example", ((JSONObject) item).optString("value"));
                        headSchema.put(PropertyConstant.TYPE, PropertyConstant.STRING);
                        head.put("schema", headSchema);
                        headers.put(((JSONObject) item).optString("key"), head);
                    }
                }
            }
            String statusCode = responseJSONObject.optString("statusCode");
            if (StringUtils.isNotBlank(statusCode)) {
                JSONObject statusCodeInfo = new JSONObject();
                statusCodeInfo.put("headers", headers);
                statusCodeInfo.put("content", buildContent(responseJSONObject, schemas));
                statusCodeInfo.put("description", StringUtils.EMPTY);
                if (StringUtils.isNotBlank(responseJSONObject.optString("value"))) {
                    statusCodeInfo.put("description", responseJSONObject.optString("value"));
                }
                responseBody.put(statusCode, statusCodeInfo);
            }
        }
        return responseBody;
    }

    private JSONObject buildRequestBody(JSONObject request, List<JSONObject> schemas) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("content", buildContent(request, schemas));
        return requestBody;
    }

    private JSONObject buildContent(JSONObject respOrReq, List<JSONObject> schemas) {
        Hashtable<String, String> typeMap = new Hashtable<String, String>() {{
            put(RequestBody.BodyType.XML.name(), MediaType.APPLICATION_XML_VALUE);
            put(RequestBody.BodyType.JSON.name(), MediaType.APPLICATION_JSON_VALUE);
            put(RequestBody.BodyType.RAW.name(), MediaType.TEXT_PLAIN_VALUE);
            put(RequestBody.BodyType.BINARY.name(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
            put(RequestBody.BodyType.FORM_DATA.name(), MediaType.MULTIPART_FORM_DATA_VALUE);
            put(RequestBody.BodyType.WWW_FORM.name(), MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        }};
        Object bodyInfo = null;
        Object jsonInfo = null;
        JSONObject body = respOrReq.optJSONObject("body");

        if (body != null) { //  将请求体转换成相应的格式导出
            String bodyType = body.optString(PropertyConstant.BODY_TYPE);
            if (StringUtils.isNotBlank(bodyType) && bodyType.equalsIgnoreCase(RequestBody.BodyType.JSON.name())) {
                try {
                    // json
                    String jsonValue = body.optJSONObject("jsonBody").optString("jsonValue");
                    if (StringUtils.isNotBlank(jsonValue)) {
                        jsonInfo = buildJson(jsonValue);
                    }
                    // jsonSchema
                    String jsonSchema = body.optJSONObject("jsonBody").optString("jsonSchema");
                    if (StringUtils.isNotBlank(jsonSchema)) {
                        JSONObject jsonSchemaObject = JsonUtil.parseObj(jsonSchema);
                        bodyInfo = buildJsonSchema(jsonSchemaObject);
                    }
                } catch (Exception e1) {    //  若请求体 json 不合法，则忽略错误，原样字符串导出/导入
                    bodyInfo = new JSONObject();
                    ((JSONObject) bodyInfo).put(PropertyConstant.TYPE, PropertyConstant.STRING);
                    if (body != null && body.optString("rawBody") != null) {
                        ((JSONObject) bodyInfo).put("example", body.optString("rawBody"));
                    }
                }
            } else if (bodyType != null && bodyType.equalsIgnoreCase(RequestBody.BodyType.RAW.name())) {
                bodyInfo = new JSONObject();
                ((JSONObject) bodyInfo).put(PropertyConstant.TYPE, PropertyConstant.STRING);
                if (body != null && body.optString("rawBody") != null) {
                    ((JSONObject) bodyInfo).put("example", body.optJSONObject("rawBody").optString("value"));
                }
            } else if (bodyType != null && bodyType.equalsIgnoreCase(RequestBody.BodyType.XML.name())) {
                String xmlText = body.optJSONObject("xmlBody").optString("value");
                String xml = XMLUtil.delXmlHeader(xmlText);
                int startIndex = xml.indexOf("<", 0);
                int endIndex = xml.indexOf(">", 0);
                if (endIndex > startIndex + 1) {
                    String substring = xml.substring(startIndex + 1, endIndex);
                    bodyInfo = buildRefSchema(substring);
                }
                JSONObject xmlToJson = XMLUtil.xmlConvertJson(xmlText);
                JSONObject jsonObject = buildRequestBodyXmlSchema(xmlToJson);
                if (schemas == null) {
                    schemas = new LinkedList<>();
                }
                schemas.add(jsonObject);
                jsonInfo = xml;
            } else if (bodyType != null && bodyType.equalsIgnoreCase(RequestBody.BodyType.WWW_FORM.name())) {
                String wwwFormBody = body.optString("wwwFormBody");
                JSONObject wwwFormObject = JsonUtil.parseObj(wwwFormBody);
                JSONObject formData = getFormDataProperties(wwwFormObject.optJSONArray("formValues"));
                bodyInfo = buildFormDataSchema(formData);
            } else if (bodyType != null && bodyType.equalsIgnoreCase(RequestBody.BodyType.FORM_DATA.name())) {
                String formDataBody = body.optString("formDataBody");
                JSONObject formDataObject = JsonUtil.parseObj(formDataBody);
                JSONObject formData = getFormDataProperties(formDataObject.optJSONArray("formValues"));
                bodyInfo = buildFormDataSchema(formData);
            } else if (bodyType != null && bodyType.equalsIgnoreCase(RequestBody.BodyType.BINARY.name())) {
                bodyInfo = buildBinary();
            }
        }

        String type = null;
        if (respOrReq.optJSONObject("body") != null) {
            type = respOrReq.optJSONObject("body").optString(PropertyConstant.BODY_TYPE);
        }
        JSONObject content = new JSONObject();
        Object schema = bodyInfo;   //  请求体部分
        JSONObject typeName = new JSONObject();
        if (schema != null) {
            typeName.put("schema", schema);
        }
        if (jsonInfo != null) {
            typeName.put("example", jsonInfo);
        }
        if (StringUtils.isNotBlank(type) && typeMap.containsKey(type)) {
            content.put(typeMap.get(type), typeName);
        }
        return content;
    }

    private JSONObject buildBinary() {
        JSONObject parsedParam = new JSONObject();
        parsedParam.put(PropertyConstant.TYPE, PropertyConstant.STRING);
        parsedParam.put("format", "binary");
        return parsedParam;
    }

    /**
     * requestBody 中jsonSchema
     *
     * @param jsonSchemaObject
     * @return
     */
    private JSONObject buildJsonSchema(JSONObject jsonSchemaObject) {
        JSONObject parsedParam = new JSONObject();
        String type = jsonSchemaObject.optString(PropertyConstant.TYPE);
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.equals(type, PropertyConstant.OBJECT)) {
                parsedParam = jsonSchemaObject;
            } else if (StringUtils.equals(type, PropertyConstant.ARRAY)) {
                JSONArray items = jsonSchemaObject.optJSONArray(PropertyConstant.ITEMS);
                JSONObject itemProperties = new JSONObject();
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.ARRAY);
                if (items != null) {
                    JSONObject itemsObject = new JSONObject();
                    if (!items.isEmpty()) {
                        items.forEach(item -> {
                            if (item instanceof JSONObject) {
                                JSONObject itemJson = buildJsonSchema((JSONObject) item);
                                if (itemJson != null) {
                                    Set<String> keys = itemJson.keySet();
                                    for (String key : keys) {
                                        itemProperties.put(key, itemJson.get(key));
                                    }
                                }
                            }
                        });
                    }
                    itemsObject.put(PropertyConstant.PROPERTIES, itemProperties);
                    parsedParam.put(PropertyConstant.ITEMS, itemsObject.optJSONObject(PropertyConstant.PROPERTIES));
                } else {
                    parsedParam.put(PropertyConstant.ITEMS, new JSONObject());
                }
            } else if (StringUtils.equals(type, PropertyConstant.INTEGER)) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.INTEGER);
                parsedParam.put("format", "int64");
                setCommonJsonSchemaParam(parsedParam, jsonSchemaObject);

            } else if (StringUtils.equals(type, PropertyConstant.BOOLEAN)) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.BOOLEAN);
                setCommonJsonSchemaParam(parsedParam, jsonSchemaObject);
            } else if (StringUtils.equals(type, PropertyConstant.NUMBER)) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.NUMBER);
                setCommonJsonSchemaParam(parsedParam, jsonSchemaObject);
            } else {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.STRING);
                setCommonJsonSchemaParam(parsedParam, jsonSchemaObject);
            }

        }
        return parsedParam;
    }

    /**
     * requestBody 中json
     *
     * @param jsonValue
     * @return
     */
    private JSONObject buildJson(String jsonValue) {
        return JsonUtil.parseObj(jsonValue);
    }

    private JSONObject getFormDataProperties(JSONArray requestBody) {
        // todo  maxLength  minLength
        JSONObject result = new JSONObject();
        for (Object item : requestBody) {
            if (item instanceof JSONObject) {
                String name = ((JSONObject) item).optString("key");
                if (name != null) {
                    result.put(name, item);
                }
            }
        }
        return result;
    }

    private static JSONObject buildRequestBodyXmlSchema(JSONObject requestBody) {
        if (requestBody == null) return null;
        JSONObject schema = new JSONObject();
        for (String key : requestBody.keySet()) {
            Object param = requestBody.get(key);
            JSONObject parsedParam = new JSONObject();

            schema.put(key, parsedParam);
        }

        return schema;
    }

    private static JSONObject buildXmlProperties(JSONObject kvs) {
        JSONObject properties = new JSONObject();
        for (String key : kvs.keySet()) {
            JSONObject property = new JSONObject();
            Object param = kvs.opt(key);
            if (param instanceof String) {
                property.put(PropertyConstant.TYPE, PropertyConstant.STRING);
                property.put("example", ObjectUtils.defaultIfNull(param, StringUtils.EMPTY));
            }
            if (param instanceof JSONObject ) {
                JSONObject obj = (JSONObject) param;
                property.put(PropertyConstant.TYPE, StringUtils.isNotEmpty(obj.optString(PropertyConstant.TYPE)) ? obj.optString(PropertyConstant.TYPE) : PropertyConstant.STRING);
                String value = obj.optString("value");
                if (StringUtils.isBlank(value)) {
                    JSONObject mock = obj.optJSONObject(PropertyConstant.MOCK);
                    if (mock != null) {
                        Object mockValue = mock.get(PropertyConstant.MOCK);
                        property.put("example", mockValue);
                    } else {
                        property.put("example", value);
                    }
                } else {
                    property.put("example", value);
                }
            }
            JSONObject xml = new JSONObject();
            xml.put("attribute", true);
            property.put("xml", xml);
            properties.put(key, property);
        }

        return properties;
    }

    private Object buildRefSchema(String substring) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("$ref", "#/components/schemas/" + substring);
        return jsonObject;
    }

    private JSONObject buildRequestBodyJsonInfo(JSONObject requestBody) {
        if (requestBody == null) return null;
        JSONObject schema = new JSONObject();
        schema.put(PropertyConstant.TYPE, PropertyConstant.OBJECT);
        JSONObject properties = buildSchema(requestBody);
        schema.put(PropertyConstant.PROPERTIES, properties);
        return schema;
    }

    private JSONObject buildSchema(JSONObject requestBody) {
        JSONObject schema = new JSONObject();
        for (String key : requestBody.keySet()) {
            Object param = requestBody.get(key);
            JSONObject parsedParam = new JSONObject();

            if (param instanceof String) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.STRING);
                parsedParam.put("example", ObjectUtils.defaultIfNull(param, StringUtils.EMPTY));
            } else if (param instanceof Integer) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.INTEGER);
                parsedParam.put("format", "int64");
                parsedParam.put("example", param);
            } else if (param instanceof JSONObject) {
                parsedParam = buildRequestBodyJsonInfo((JSONObject) param);
            } else if (param instanceof Boolean) {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.BOOLEAN);
                parsedParam.put("example", param);
            } else if (param instanceof BigDecimal) {
                parsedParam.put(PropertyConstant.TYPE, "double");
                parsedParam.put("example", param);   //  double 类型会被 fastJson 转换为 BigDecimal
            } else {
                parsedParam.put(PropertyConstant.TYPE, PropertyConstant.ARRAY);
                JSONObject item = new JSONObject();
                if (param == null) {
                    param = new JSONArray();
                }
                if (param instanceof JSONArray && !((JSONArray) param).isEmpty()) {
                    if (((JSONArray) param).get(0) instanceof JSONObject) {
                        item = buildRequestBodyJsonInfo((JSONObject) ((JSONArray) param).get(0));
                    }
                }
                parsedParam.put(PropertyConstant.ITEMS, item);     //  JSONOArray
            }

            schema.put(key, parsedParam);
        }
        return schema;
    }


    private JSONObject buildFormDataSchema(JSONObject kvs) {
        JSONObject schema = new JSONObject();
        JSONObject properties = new JSONObject();
        for (String key : kvs.keySet()) {
            JSONObject property = new JSONObject();
            JSONObject obj = ((JSONObject) kvs.get(key));
            property.put(PropertyConstant.TYPE, StringUtils.isNotEmpty(obj.optString(PropertyConstant.PARAM_TYPE)) ? obj.optString(PropertyConstant.PARAM_TYPE) : PropertyConstant.STRING);
            String value = obj.optString("value");
            if (StringUtils.isBlank(value)) {
                JSONObject mock = obj.optJSONObject(PropertyConstant.MOCK);
                if (mock != null && StringUtils.isNotBlank(mock.optString("mock"))) {
                    Object mockValue = mock.get(PropertyConstant.MOCK);
                    property.put("example", mockValue);
                } else {
                    property.put("example", value);
                }
            } else {
                property.put("example", value);
            }
            property.put("description", obj.optString("description"));
            property.put(PropertyConstant.REQUIRED, obj.optString(PropertyConstant.REQUIRED));
            if (obj.optJSONObject(PropertyConstant.PROPERTIES) != null) {
                JSONObject childProperties = buildFormDataSchema(obj.optJSONObject(PropertyConstant.PROPERTIES));
                property.put(PropertyConstant.PROPERTIES, childProperties.optJSONObject(PropertyConstant.PROPERTIES));
            } else {
                JSONObject childProperties = buildJsonSchema(obj);
                if (StringUtils.equalsIgnoreCase(obj.optString(PropertyConstant.PARAM_TYPE), PropertyConstant.ARRAY)) {
                    if (childProperties.optJSONObject(PropertyConstant.ITEMS) != null) {
                        property.put(PropertyConstant.ITEMS, childProperties.optJSONObject(PropertyConstant.ITEMS));
                    }
                } else {
                    if (childProperties.optJSONObject(PropertyConstant.PROPERTIES) != null) {
                        property.put(PropertyConstant.PROPERTIES, childProperties.optJSONObject(PropertyConstant.PROPERTIES));
                    }
                }
            }

            if (StringUtils.isNotBlank(obj.optString("maxLength"))) {
                property.put("maxLength", obj.optInt("maxLength"));
            }
            if (StringUtils.isNotBlank(obj.optString("minLength"))) {
                property.put("minLength", obj.optInt("minLength"));
            }
            properties.put(key, property);
        }
        schema.put(PropertyConstant.PROPERTIES, properties);
        return schema;
    }

    public void setCommonJsonSchemaParam(JSONObject parsedParam, JSONObject requestBody) {
        if (StringUtils.isNotBlank(requestBody.optString("description"))) {
            parsedParam.put("description", requestBody.optString("description"));
        }
        Object jsonSchemaValue = getJsonSchemaValue(requestBody);
        if (jsonSchemaValue != null) {
            parsedParam.put("example", jsonSchemaValue);
        }
    }

    public Object getJsonSchemaValue(JSONObject item) {
        JSONObject mock = item.optJSONObject(PropertyConstant.MOCK);
        if (mock != null) {
            if (StringUtils.isNotBlank(mock.optString("mock"))) {
                return mock.get(PropertyConstant.MOCK);
            }
        }
        return null;
    }


}

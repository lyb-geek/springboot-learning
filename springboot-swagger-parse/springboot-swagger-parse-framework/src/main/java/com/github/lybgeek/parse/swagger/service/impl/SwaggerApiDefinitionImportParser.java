package com.github.lybgeek.parse.swagger.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.github.lybgeek.parse.swagger.api.ApiDefinitionImportParserResult;
import com.github.lybgeek.parse.swagger.api.definition.ApiDefinitionDetail;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormDataBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormDataKV;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.WWWFormBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.WWWFormKV;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.json.JsonBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.json.JsonSchemaItem;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.none.NoneBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.raw.RawBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.xml.XmlBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.header.Header;
import com.github.lybgeek.parse.swagger.api.http.metadata.param.query.QueryParam;
import com.github.lybgeek.parse.swagger.api.http.metadata.param.rest.RestParam;
import com.github.lybgeek.parse.swagger.api.http.request.HttpRequest;
import com.github.lybgeek.parse.swagger.api.http.request.RequestBody;
import com.github.lybgeek.parse.swagger.api.http.response.HttpResponse;
import com.github.lybgeek.parse.swagger.api.http.response.ResponseBody;
import com.github.lybgeek.parse.swagger.auth.NoAuth;
import com.github.lybgeek.parse.swagger.constant.ApiConstants;
import com.github.lybgeek.parse.swagger.constant.HttpMethod;
import com.github.lybgeek.parse.swagger.constant.MediaType;
import com.github.lybgeek.parse.swagger.constant.PropertyConstant;
import com.github.lybgeek.parse.swagger.dto.ImportRequest;
import com.github.lybgeek.parse.swagger.service.ApiDefinitionImportParser;
import com.github.lybgeek.parse.swagger.util.ApiDefinitionImportUtil;
import com.github.lybgeek.parse.swagger.util.json.JsonUtil;
import com.github.lybgeek.parse.swagger.util.xml.XMLUtil;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.*;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.parser.core.models.AuthorizationValue;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SwaggerApiDefinitionImportParser implements ApiDefinitionImportParser<ApiDefinitionImportParserResult> {

    private Components components;

    public static final String PATH = "path";
    public static final String HEADER = "header";
    public static final String COOKIE = "cookie";
    public static final String QUERY = "query";

    private void testUrlTimeout(String swaggerUrl) {
        HttpURLConnection connection = null;
        try {
            URI uriObj = new URI(swaggerUrl);
            connection = (HttpURLConnection) uriObj.toURL().openConnection();
            connection.setUseCaches(false);
            connection.setConnectTimeout(3000); // 设置超时时间
            connection.connect(); // 建立连接
        } catch (Exception e) {
            log.error("请求swaggerUrl异常："+e.getMessage(), e);
            throw new IllegalArgumentException("请求swaggerUrl异常："+e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect(); // 关闭连接
            }
        }
    }


    @Override
    public ApiDefinitionImportParserResult parse(InputStream source, ImportRequest request) throws Exception {
        //将之前在service中的swagger地址判断放在这里。
        if (StringUtils.isNotBlank(request.getSwaggerUrl())) {
            this.testUrlTimeout(request.getSwaggerUrl());
        }

        log.info("Swagger3Parser parse");
        List<AuthorizationValue> auths = setAuths(request);
        SwaggerParseResult result = null;
        if (StringUtils.isNotBlank(request.getSwaggerUrl())) {
            result = new OpenAPIParser().readLocation(request.getSwaggerUrl(), auths, null);
            if (result == null || result.getOpenAPI() == null || !result.getOpenAPI().getOpenapi().startsWith("3.0") || result.isOpenapi31()) {
                throw new IllegalAccessException("swagger" + request.getSwaggerUrl() + " parse error with auth " + CollectionUtil.join(auths, ","));
            }
        } else if(StringUtils.isNotBlank(request.getSwaggerFilePath())){
            result = new OpenAPIParser().readLocation(request.getSwaggerFilePath(), auths, null);
        } else {
            String apiTestStr = getApiTestStr(source);
            Map<String, Object> o = JsonUtil.parseMap(apiTestStr);
            // 判断属性 swagger的值是不是3.0开头
            if (o instanceof Map ) {
                Map map = (Map) o;
                if (map.containsKey("swagger") && !map.get("swagger").toString().startsWith("3.0")) {
                    throw new IllegalArgumentException("swagger version error");
                }
            }
            result = new OpenAPIParser().readContents(apiTestStr, null, null);
            if (result == null || result.getOpenAPI() == null || !result.getOpenAPI().getOpenapi().startsWith("3.0") || result.isOpenapi31()) {
                throw new IllegalArgumentException("swagger parse error");
            }
        }
        ApiDefinitionImportParserResult apiDefinitionImportParseResult = new ApiDefinitionImportParserResult();
        OpenAPI openAPI = result.getOpenAPI();
        apiDefinitionImportParseResult.setData(parseRequests(openAPI, request));
        return apiDefinitionImportParseResult;
    }

    private List<ApiDefinitionDetail> parseRequests(OpenAPI openAPI, ImportRequest importRequest) {

        Paths paths = openAPI.getPaths();

        Set<String> pathNames = paths.keySet();

        this.components = openAPI.getComponents();

        List<ApiDefinitionDetail> results = new ArrayList<>();

        for (String pathName : pathNames) {
            PathItem pathItem = paths.get(pathName);

            Map<String, Operation> operationsMap = new HashMap<>();
            operationsMap.put(HttpMethod.GET.name(), pathItem.getGet());
            operationsMap.put(HttpMethod.POST.name(), pathItem.getPost());
            operationsMap.put(HttpMethod.DELETE.name(), pathItem.getDelete());
            operationsMap.put(HttpMethod.PUT.name(), pathItem.getPut());
            operationsMap.put(HttpMethod.PATCH.name(), pathItem.getPatch());
            operationsMap.put(HttpMethod.HEAD.name(), pathItem.getHead());
            operationsMap.put(HttpMethod.OPTIONS.name(), pathItem.getOptions());
            operationsMap.put(HttpMethod.TRACE.name(), pathItem.getTrace());

            for (String method : operationsMap.keySet()) {
                Operation operation = operationsMap.get(method);
                if (operation != null) {
                    //构建基本请求
                    ApiDefinitionDetail apiDefinitionDTO = buildSwaggerApiDefinition(operation, pathName, method, importRequest);
                    //构建请求参数
                    HttpRequest request = buildHttpRequest(apiDefinitionDTO.getName(), pathName, method);
                    parseParameters(operation, request);
                    parseParameters(pathItem, request);
                    //构建请求体
                    parseRequestBody(operation.getRequestBody(), request);

                    //认证
                    request.setAuthConfig(new NoAuth());


                    //解析请求内容
                    parseResponse(operation.getResponses(), apiDefinitionDTO.getResponse(), request.getHeaders());
                    apiDefinitionDTO.setRequest(request);
                    results.add(apiDefinitionDTO);
                }
            }
        }

        return results;
    }

    private void parseResponse(ApiResponses responseBody, List<HttpResponse> response, List<Header> requestHeaders) {
        if (responseBody != null) {
            responseBody.forEach((key, value) -> {
                HttpResponse httpResponse = new HttpResponse();
                //TODO headers
                httpResponse.setStatusCode(StringUtils.equals("default", key) ? "200" : key);
                ResponseBody body = new ResponseBody();
                Map<String, io.swagger.v3.oas.models.headers.Header> headers = value.getHeaders();
                if (MapUtil.isNotEmpty(headers)) {
                    List<Header> headerList = new ArrayList<>();
                    headers.forEach((k, v) -> {
                        Header header = new Header();
                        header.setKey(k);
                        header.setValue(getDefaultObjectValue(v.getExample()));
                        header.setDescription(getDefaultStringValue(v.getDescription()));
                        headerList.add(header);
                    });
                    httpResponse.setHeaders(headerList);
                }
                if (value.getContent() != null) {
                    value.getContent().forEach((k, v) -> {
                        setResponseBodyData(k, v, body);
                        if (requestHeaders.stream().noneMatch(header -> StringUtils.equals(header.getKey(), ApiConstants.ACCEPT))) {
                            Header header = new Header();
                            header.setKey(ApiConstants.ACCEPT);
                            header.setValue(k);
                            requestHeaders.add(header);
                        }
                    });
                } else {
                    body.setBodyType(RequestBody.BodyType.NONE.name());
                }
                httpResponse.setBody(body);
                httpResponse.setId(IdUtil.randomUUID());
                response.add(httpResponse);
            });
            // 判断  如果是200  默认defaultFlag为true 否则的话  随机挑一个为true
            if (CollectionUtil.isNotEmpty(response)) {
                response.forEach(httpResponse -> {
                    if (StringUtils.equals("200", httpResponse.getStatusCode())) {
                        httpResponse.setDefaultFlag(true);
                    }
                });
                if (response.stream().noneMatch(httpResponse -> StringUtils.equals("200", httpResponse.getStatusCode()))) {
                    response.get(0).setDefaultFlag(true);
                }
            }
        }

    }

    private void setResponseBodyData(String k, io.swagger.v3.oas.models.media.MediaType value, ResponseBody body) {
        JsonSchemaItem jsonSchemaItem = parseSchema(value.getSchema(), new HashSet<>());
        switch (k) {
            case MediaType.APPLICATION_JSON_VALUE:
            case MediaType.ALL_VALUE:
                body.setBodyType(RequestBody.BodyType.JSON.name());
                body.setJsonBody(getJsonBody(value, jsonSchemaItem));
                break;
            case MediaType.APPLICATION_XML_VALUE:
                body.setBodyType(RequestBody.BodyType.XML.name());
                XmlBody xml = new XmlBody();
                try {
                    String xmlBody = parseXmlBody(value, jsonSchemaItem);
                    xml.setValue(xmlBody);
                } catch (Exception e) {
                    xml.setValue(e.getMessage());
                }
                body.setXmlBody(xml);
                break;
            case MediaType.MULTIPART_FORM_DATA_VALUE:
                body.setBodyType(RequestBody.BodyType.FORM_DATA.name());
                break;
            case MediaType.APPLICATION_OCTET_STREAM_VALUE:
                body.setBodyType(RequestBody.BodyType.BINARY.name());
                break;
            case MediaType.TEXT_PLAIN_VALUE:
                body.setBodyType(RequestBody.BodyType.RAW.name());
                RawBody rawBody = new RawBody();
                if (Objects.nonNull(value.getSchema().getExample())) {
                    rawBody.setValue(value.getSchema().getExample().toString());
                }
                body.setRawBody(rawBody);
                break;
            default:
                body.setBodyType(RequestBody.BodyType.NONE.name());
                break;
        }

    }

    private void parseParameters(Operation operation, HttpRequest request) {

        List<Parameter> parameters = operation.getParameters();

        if (CollectionUtil.isEmpty(parameters)) {
            return;
        }
        parameters.forEach(parameter -> {
            if (parameter instanceof QueryParameter) {
                QueryParameter queryParameter = (QueryParameter) parameter;
                parseQueryParameters(queryParameter, request.getQuery());
            } else if (parameter instanceof PathParameter) {
                PathParameter pathParameter = (PathParameter) parameter;
                parsePathParameters(pathParameter, request.getRest());
            } else if (parameter instanceof HeaderParameter) {
                HeaderParameter headerParameter = (HeaderParameter) parameter;
                parseHeaderParameters(headerParameter, request.getHeaders());
            } else if (parameter instanceof CookieParameter) {
                CookieParameter cookieParameter = (CookieParameter) parameter;
                parseCookieParameters(cookieParameter, request.getHeaders());
            } else {
                // 默认情况下的处理
            }
        });
    }

    private void parseParameters(PathItem path, HttpRequest request) {
        if (path.getParameters() == null) {
            return;
        }
        List<Parameter> parameters = path.getParameters();
        // 处理特殊格式  rest参数是和请求平级的情况

        for (Parameter parameter : parameters) {
            if (StringUtils.isNotBlank(parameter.getIn())) {
                switch (parameter.getIn()) {
                    case "PATH":
                        parsePathParameters((PathParameter) parameter, request.getRest());
                        break;
                    case "QUERY":
                        parseQueryParameters((QueryParameter) parameter, request.getQuery());
                        break;
                    case "HEADER":
                        parseHeaderParameters((HeaderParameter) parameter, request.getHeaders());
                        break;
                    case "COOKIE":
                        parseCookieParameters((CookieParameter) parameter, request.getHeaders());
                        break;
                    default:
                        return;
                }
            }
        }
    }

    private void parseQueryParameters(QueryParameter queryParameter, List<QueryParam> arguments) {
        QueryParam queryParam = new QueryParam();
        queryParam.setKey(getDefaultStringValue(queryParameter.getName()));
        queryParam.setRequired(queryParameter.getRequired());
        queryParam.setDescription(getDefaultStringValue(queryParameter.getDescription()));
        if (queryParameter.getSchema() != null) {
            queryParam.setParamType(queryParameter.getSchema().getType());
            queryParam.setValue(getDefaultObjectValue(queryParameter.getSchema().getExample()));
            queryParam.setMinLength(queryParameter.getSchema().getMinLength());
            queryParam.setMaxLength(queryParameter.getSchema().getMaxLength());
        }
        if (queryParameter.getExample() != null) {
            queryParam.setValue(getDefaultObjectValue(queryParameter.getExample()));
        }
        arguments.add(queryParam);
    }

    private void parsePathParameters(PathParameter parameter, List<RestParam> rest) {
        RestParam restParam = new RestParam();
        restParam.setKey(getDefaultStringValue(parameter.getName()));
        restParam.setRequired(parameter.getRequired());
        restParam.setDescription(getDefaultStringValue(parameter.getDescription()));
        if (parameter.getSchema() != null) {
            restParam.setParamType(parameter.getSchema().getType());
            restParam.setValue(getDefaultObjectValue(parameter.getSchema().getExample()));
            restParam.setMinLength(parameter.getSchema().getMinLength());
            restParam.setMaxLength(parameter.getSchema().getMaxLength());
        }
        if (parameter.getExample() != null) {
            restParam.setValue(getDefaultObjectValue(parameter.getExample()));
        }
        rest.add(restParam);
    }

    private void parseHeaderParameters(HeaderParameter headerParameter, List<Header> headers) {
        Header headerParams = new Header();
        headerParams.setKey(getDefaultStringValue(headerParameter.getName()));
        headerParams.setDescription(getDefaultStringValue(headerParameter.getDescription()));
        if (headerParameter.getSchema() != null) {
            headerParams.setValue(getDefaultObjectValue(headerParameter.getSchema().getExample()));
        }
        if (headerParameter.getExample() != null) {
            headerParams.setValue(getDefaultObjectValue(headerParameter.getExample()));
        }
        headers.add(headerParams);
    }

    private void parseCookieParameters(CookieParameter cookieParameter, List<Header> headers) {
        Header headerParams = new Header();
        headerParams.setKey(getDefaultStringValue(cookieParameter.getName()));
        headerParams.setDescription(getDefaultStringValue(cookieParameter.getDescription()));
        if (cookieParameter.getSchema() != null) {
            headerParams.setValue(getDefaultObjectValue(cookieParameter.getSchema().getExample()));
        }
        if (cookieParameter.getExample() != null) {
            headerParams.setValue(getDefaultObjectValue(cookieParameter.getExample()));
        }
        headers.add(headerParams);
    }

    private String getDefaultStringValue(String val) {
        return StringUtils.isBlank(val) ? StringUtils.EMPTY : val;
    }

    private String getDefaultObjectValue(Object val) {
        return val == null ? StringUtils.EMPTY : val.toString();
    }


    protected HttpRequest buildHttpRequest(String name, String path, String method) {
        return ApiDefinitionImportUtil.buildHttpRequest(name, path, method);
    }


    private ApiDefinitionDetail buildSwaggerApiDefinition(Operation operation, String path, String
            method, ImportRequest importRequest) {
        String name;
        if (StringUtils.isNotBlank(operation.getSummary())) {
            name = operation.getSummary();
        } else if (StringUtils.isNotBlank(operation.getOperationId())) {
            name = operation.getOperationId();
        } else {
            name = path;
        }
        String modulePath = StringUtils.EMPTY;
        if (CollectionUtil.isNotEmpty(operation.getTags())) {
            modulePath = operation.getTags().get(0);
            if (!StringUtils.startsWith(modulePath, "/")) {
                modulePath = "/" + modulePath;
            }
        }
        return buildApiDefinition(name, path, method, modulePath, importRequest);
    }

    protected ApiDefinitionDetail buildApiDefinition(String name, String path, String method, String modulePath, ImportRequest importRequest) {
        ApiDefinitionDetail apiDefinition = new ApiDefinitionDetail();
        apiDefinition.setId(IdUtil.randomUUID());
        if (name != null) {
            apiDefinition.setName(StringUtils.trim(name));
            if (apiDefinition.getName().length() > 255) {
                apiDefinition.setName(apiDefinition.getName().substring(0, 250) + "...");
            }
        }
        apiDefinition.setPath(ApiDefinitionImportUtil.formatPath(StringUtils.trim(path)));
        apiDefinition.setProtocol(StringUtils.trim(importRequest.getProtocol()));
        apiDefinition.setMethod(StringUtils.trim(method));
        apiDefinition.setProjectId(StringUtils.trim(importRequest.getProjectId()));
        apiDefinition.setModulePath(StringUtils.trim(modulePath));
        apiDefinition.setResponse(new ArrayList<>());
        return apiDefinition;
    }

    private void parseRequestBody(io.swagger.v3.oas.models.parameters.RequestBody requestBody, HttpRequest request) {
        if (requestBody != null) {
            Content content = requestBody.getContent();
            if (content != null) {
                List<Header> headers = request.getHeaders();
                Iterator<Map.Entry<String, io.swagger.v3.oas.models.media.MediaType>> iterator = content.entrySet().iterator();
                if (iterator.hasNext()) {
                    // 优先获取第一个
                    Map.Entry<String, io.swagger.v3.oas.models.media.MediaType> mediaType = iterator.next();
                    setRequestBodyData(mediaType.getKey(), mediaType.getValue(), request.getBody());
                    // 如果key不包含Content-Type  则默认添加Content-Type
                    if (headers.stream().noneMatch(header -> StringUtils.equals(header.getKey(), ApiConstants.CONTENT_TYPE))) {
                        Header header = new Header();
                        header.setKey(ApiConstants.CONTENT_TYPE);
                        header.setValue(mediaType.getKey());
                        headers.add(header);
                    }
                }
            } else {
                request.getBody().setBodyType(RequestBody.BodyType.NONE.name());
                request.getBody().setNoneBody(new NoneBody());
            }
        } else {
            request.getBody().setBodyType(RequestBody.BodyType.NONE.name());
            request.getBody().setNoneBody(new NoneBody());
        }
    }

    private void setRequestBodyData(String k, io.swagger.v3.oas.models.media.MediaType value, RequestBody body) {
        JsonSchemaItem jsonSchemaItem = parseSchema(value.getSchema(), new HashSet<>());
        switch (k) {
            case MediaType.APPLICATION_JSON_VALUE:
            case MediaType.ALL_VALUE:
                body.setBodyType(RequestBody.BodyType.JSON.name());
                body.setJsonBody(getJsonBody(value, jsonSchemaItem));
                break;
            case MediaType.APPLICATION_XML_VALUE:
                body.setBodyType(RequestBody.BodyType.XML.name());
                XmlBody xml = new XmlBody();
                String xmlBody = parseXmlBody(value, jsonSchemaItem);
                xml.setValue(xmlBody);
                body.setXmlBody(xml);
                break;
            case MediaType.APPLICATION_FORM_URLENCODED_VALUE:
                body.setBodyType(RequestBody.BodyType.WWW_FORM.name());
                parseWWWFormBody(jsonSchemaItem, body);
                break;
            case MediaType.MULTIPART_FORM_DATA_VALUE:
                body.setBodyType(RequestBody.BodyType.FORM_DATA.name());
                parseFormBody(jsonSchemaItem, body);
                break;
            case MediaType.APPLICATION_OCTET_STREAM_VALUE:
                body.setBodyType(RequestBody.BodyType.BINARY.name());
                break;
            case MediaType.TEXT_PLAIN_VALUE:
                body.setBodyType(RequestBody.BodyType.RAW.name());
                RawBody rawBody = new RawBody();
                if (Objects.nonNull(value.getSchema().getExample())) {
                    rawBody.setValue(value.getSchema().getExample().toString());
                }
                body.setRawBody(rawBody);
                break;
            default:
                body.setBodyType(RequestBody.BodyType.NONE.name());
                break;
        }

    }

    private void parseFormBody(JsonSchemaItem item, RequestBody body) {
        FormDataBody formDataBody = new FormDataBody();
        if (item == null) {
            body.setFormDataBody(formDataBody);
            return;
        }
        List<String> required = item.getRequired();
        List<FormDataKV> formDataKVS = new ArrayList<>();
        item.getProperties().forEach((key, value) -> {
            if (value != null && !StringUtils.equals(PropertyConstant.OBJECT, value.getType())) {
                FormDataKV formDataKV = new FormDataKV();
                formDataKV.setKey(key);
                formDataKV.setValue(value.getExample());
                formDataKV.setRequired(CollectionUtil.isNotEmpty(required) && required.contains(key));
                formDataKV.setDescription(value.getDescription());
                formDataKV.setParamType(value.getType());
                formDataKV.setMinLength(value.getMinLength());
                formDataKV.setMaxLength(value.getMaxLength());
                if (StringUtils.equals(value.getType(), PropertyConstant.FILE)) {
                    formDataKV.setFiles(new ArrayList<>());
                }
                formDataKVS.add(formDataKV);
            }
        });
        formDataBody.setFormValues(formDataKVS);
        body.setFormDataBody(formDataBody);
    }

    private JsonBody getJsonBody(io.swagger.v3.oas.models.media.MediaType value, JsonSchemaItem jsonSchemaItem) {
        JsonBody jsonBody = new JsonBody();
        jsonBody.setJsonSchema(jsonSchemaItem);
        if (Objects.nonNull(value.getExample())) {
            jsonBody.setJsonValue(JsonUtil.toJSONString(value.getExample()));
        } else {
            String jsonString = JsonUtil.toJSONString(jsonSchemaItem);
            if (StringUtils.isNotBlank(jsonString)) {
                jsonBody.setJsonValue(JsonUtil.jsonSchemaToJson(jsonString, true));
            }
        }
        return jsonBody;
    }

    private String parseXmlBody(io.swagger.v3.oas.models.media.MediaType value, JsonSchemaItem jsonSchemaItem) {
        Schema schema = value.getSchema();
        JSONObject object = new JSONObject();
        if (value.getExample() != null) {
            return value.getExample().toString();
        }

        if (jsonSchemaItem != null && MapUtil.isNotEmpty(jsonSchemaItem.getProperties())) {
            if (StringUtils.isNotBlank(schema.get$ref()) && schema.get$ref().split("/").length > 3) {
                String ref = schema.get$ref().replace("#/components/schemas/", StringUtils.EMPTY);
                object.put(ref, jsonSchemaItem.getProperties());
                return XMLUtil.jsonToPrettyXml(object);
            }
        } else {
            if (schema != null && StringUtils.isNotBlank(schema.getName())) {
                object.put(schema.getName(), schema.getExample());
            }
        }

        return XMLUtil.jsonToPrettyXml(object);
    }


    private void parseWWWFormBody(JsonSchemaItem item, RequestBody body) {
        WWWFormBody wwwFormBody = new WWWFormBody();
        if (item == null) {
            body.setWwwFormBody(wwwFormBody);
            return;
        }
        List<String> required = item.getRequired();
        List<WWWFormKV> formDataKVS = new ArrayList<>();
        item.getProperties().forEach((key, value) -> {
            if (value != null && !StringUtils.equals(PropertyConstant.OBJECT, value.getType())) {
                FormDataKV formDataKV = new FormDataKV();
                formDataKV.setKey(key);
                formDataKV.setValue(value.getExample());
                formDataKV.setRequired(CollectionUtil.isNotEmpty(required) && required.contains(key));
                formDataKV.setDescription(value.getDescription());
                formDataKV.setParamType(value.getType());
                formDataKV.setMinLength(value.getMinLength());
                formDataKV.setMaxLength(value.getMaxLength());
                formDataKVS.add(formDataKV);
            }
        });
        wwwFormBody.setFormValues(formDataKVS);
        body.setWwwFormBody(wwwFormBody);
    }

    private JsonSchemaItem parseSchema(Schema<?> schema, Set refModelSet) {
        if (schema != null) {
            if (schema instanceof ArraySchema) {
                return parseArraySchema(schema, refModelSet);
            } else if (schema instanceof ObjectSchema) {
                return parseObject(schema, refModelSet);
            } else if (schema instanceof MapSchema) {
                return parseMapObject((MapSchema) schema, refModelSet);
            } else {
                if (StringUtils.isNotBlank(schema.get$ref())) {
                    return parseObject(schema, refModelSet);
                }
                return parseSchemaBySimpleType(schema);
            }
        }
        return null;
    }

    private JsonSchemaItem parseSchemaBySimpleType(Schema<?> schema) {
        String type = schema.getType();
        JsonSchemaItem result;

        switch (type) {
            case PropertyConstant.STRING:
                result = parseString(schema);
                break;
            case PropertyConstant.INTEGER:
                result = parseInteger(schema);
                break;
            case PropertyConstant.NUMBER:
                result = parseNumber(schema);
                break;
            case PropertyConstant.BOOLEAN:
                result = parseBoolean(schema);
                break;
            default:
                JsonSchemaItem jsonSchemaItem = new JsonSchemaItem();
                jsonSchemaItem.setId(IdUtil.randomUUID());
                if (StringUtils.isNotBlank(schema.getType())) {
                    jsonSchemaItem.setType(schema.getType());
                }
                result = jsonSchemaItem;
                break;
        }

        return result;

    }


    private JsonSchemaItem parseArraySchema(Schema arraySchema, Set refModelSet) {
        JsonSchemaItem jsonSchemaArray = new JsonSchemaItem();
        jsonSchemaArray.setType(PropertyConstant.ARRAY);
        jsonSchemaArray.setId(IdUtil.randomUUID());
        jsonSchemaArray.setMaxItems(arraySchema.getMaxItems());
        jsonSchemaArray.setMinItems(arraySchema.getMinItems());

        JsonSchemaItem itemsJsonSchema = parseProperty(arraySchema.getItems(), refModelSet);
        jsonSchemaArray.setItems(ListUtil.of(itemsJsonSchema));
        return jsonSchemaArray;
    }

    private JsonSchemaItem parseProperty(Schema<?> value, Set refModelSet) {
        if (StringUtils.equals(value.getType(), PropertyConstant.NULL)) {
            return parseNull();
        }

        if (value instanceof IntegerSchema) {
            return parseInteger(value);
        } else if (value instanceof StringSchema) {
            return parseString(value);
        } else if (value instanceof NumberSchema) {
            return parseNumber(value);
        } else if (value instanceof BooleanSchema) {
            return parseBoolean(value);
        } else if (value instanceof ArraySchema) {
            return parseArraySchema(value, refModelSet);
        } else if (value instanceof ObjectSchema) {
            return parseObject(value, refModelSet);
        } else if (value instanceof MapSchema) {
            return parseMapObject((MapSchema) value, refModelSet);
        } else if (value instanceof Schema<?>) {
            return parseSchema(value, refModelSet);
        }

        return null; // 默认返回值，如果需要可以调整
    }

    private JsonSchemaItem parseNull() {
        JsonSchemaItem jsonSchemaNull = new JsonSchemaItem();
        jsonSchemaNull.setId(IdUtil.randomUUID());
        jsonSchemaNull.setType(PropertyConstant.NULL);
        return jsonSchemaNull;
    }

    private JsonSchemaItem parseInteger(Schema integerSchema) {
        JsonSchemaItem jsonSchemaItem = parseSchemaItem(integerSchema);
        jsonSchemaItem.setType(PropertyConstant.INTEGER);
        jsonSchemaItem.setFormat(StringUtils.isNotBlank(integerSchema.getFormat()) ? integerSchema.getFormat() : StringUtils.EMPTY);
        jsonSchemaItem.setMaximum(integerSchema.getMaximum());
        jsonSchemaItem.setMinimum(integerSchema.getMinimum());
        List<Number> enumValues = integerSchema.getEnum();
        if (CollectionUtil.isNotEmpty(enumValues)) {
            jsonSchemaItem.setEnumValues(enumValues.stream().map(item -> item.toString()).collect(Collectors.toList()));
        }
        return jsonSchemaItem;
    }

    private JsonSchemaItem parseString(Schema stringSchema) {
        JsonSchemaItem jsonSchemaItem = parseSchemaItem(stringSchema);
        jsonSchemaItem.setType(PropertyConstant.STRING);
        jsonSchemaItem.setFormat(getDefaultStringValue(stringSchema.getFormat()));
        jsonSchemaItem.setDescription(getDefaultStringValue(stringSchema.getDescription()));
        jsonSchemaItem.setMaxLength(stringSchema.getMaxLength());
        jsonSchemaItem.setMinLength(stringSchema.getMinLength());
        jsonSchemaItem.setPattern(stringSchema.getPattern());
        jsonSchemaItem.setEnumValues(stringSchema.getEnum());
        return jsonSchemaItem;
    }

    private JsonSchemaItem parseNumber(Schema numberSchema) {
        JsonSchemaItem jsonSchemaItem = parseSchemaItem(numberSchema);
        jsonSchemaItem.setType(PropertyConstant.NUMBER);
        return jsonSchemaItem;
    }


    private JsonSchemaItem parseSchemaItem(Schema schema) {
        JsonSchemaItem jsonSchemaItem = new JsonSchemaItem();
        jsonSchemaItem.setId(IdUtil.randomUUID());
        jsonSchemaItem.setDescription(getDefaultStringValue(schema.getDescription()));
        Optional.ofNullable(schema.getExample()).ifPresent(example -> jsonSchemaItem.setExample(example.toString()));
        jsonSchemaItem.setEnumValues(schema.getEnum());
        jsonSchemaItem.setDefaultValue(schema.getDefault());
        return jsonSchemaItem;
    }

    private JsonSchemaItem parseBoolean(Schema booleanSchema) {
        JsonSchemaItem jsonSchemaItem = parseSchemaItem(booleanSchema);
        jsonSchemaItem.setType(PropertyConstant.BOOLEAN);
        return jsonSchemaItem;
    }



    private JsonSchemaItem parseObject(Schema objectSchema, Set refModelSet) {
        JsonSchemaItem jsonSchemaItem = new JsonSchemaItem();
        jsonSchemaItem.setType(PropertyConstant.OBJECT);
        jsonSchemaItem.setRequired(objectSchema.getRequired());
        jsonSchemaItem.setId(IdUtil.randomUUID());
        jsonSchemaItem.setDescription(objectSchema.getDescription());
        Map<String, JsonSchemaItem> jsonSchemaProperties = new LinkedHashMap<>();
        Map<String, Schema> properties = objectSchema.getProperties();
        Schema<?> refSchema = getRefSchema(objectSchema);
        if (refSchema != null) {
            if (refModelSet.contains(objectSchema.get$ref())) {
                // 如果存在循环引用，则直接返回
                return jsonSchemaItem;
            }
            properties = refSchema.getProperties();
            // 记录引用的对象
            refModelSet.add(objectSchema.get$ref());
        }
        if (MapUtil.isNotEmpty(properties)) {
            properties.forEach((key, value) -> {
                JsonSchemaItem item = parseProperty(value, refModelSet);
                jsonSchemaProperties.put(key, item);
            });
        }
        jsonSchemaItem.setProperties(jsonSchemaProperties);
        return jsonSchemaItem;
    }

    private Schema<?> getRefSchema(Schema<?> schema) {
        String refName = schema.get$ref();
        if (StringUtils.isNotBlank(refName)) {
            return getModelByRef(refName);
        }
        return null;
    }

    private Schema<?> getModelByRef(String ref) {
        if (StringUtils.isBlank(ref)) {
            return null;
        }
        if (ref.split("/").length > 3) {
            ref = ref.replace("#/components/schemas/", StringUtils.EMPTY);
        }
        if (this.components.getSchemas() != null) return this.components.getSchemas().get(ref);
        return null;
    }

    private JsonSchemaItem parseMapObject(MapSchema mapSchema, Set refModelSet) {
        JsonSchemaItem jsonSchemaItem = new JsonSchemaItem();
        jsonSchemaItem.setType(PropertyConstant.OBJECT);
        jsonSchemaItem.setRequired(mapSchema.getRequired());
        jsonSchemaItem.setDescription(mapSchema.getDescription());
        Object value = mapSchema.getAdditionalProperties();
        Map<String, JsonSchemaItem> jsonSchemaProperties = new LinkedHashMap<>();
        if (Objects.isNull(value)) {
            return jsonSchemaItem;
        }
        JsonSchemaItem item = new JsonSchemaItem();
        if (value instanceof IntegerSchema) {
            item = parseInteger((IntegerSchema) value);
        } else if (value instanceof StringSchema) {
            item = parseString((StringSchema) value);
        } else if (value instanceof NumberSchema) {
            item = parseNumber((NumberSchema) value);
        } else if (value instanceof BooleanSchema) {
            item = parseBoolean((BooleanSchema) value);
        } else if (value instanceof ArraySchema) {
            item = parseArraySchema((ArraySchema) value, refModelSet);
        } else if (value instanceof ObjectSchema) {
            item = parseObject((ObjectSchema) value, refModelSet);
        } else if (value instanceof Schema) {
            item = parseSchema((Schema) value, refModelSet);
        }
        jsonSchemaProperties.put(StringUtils.EMPTY, item);
        jsonSchemaItem.setProperties(jsonSchemaProperties);
        return jsonSchemaItem;
    }




    protected String getApiTestStr(InputStream source) {
        StringBuilder testStr = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
            testStr = new StringBuilder();
            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null) {
                testStr.append(inputStr).append("\n");
            }
            source.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return StringUtils.isNotBlank(testStr) ? testStr.toString() : StringUtils.EMPTY;
    }
    private List<AuthorizationValue> setAuths(ImportRequest request) {
        List<AuthorizationValue> auths = new ArrayList<>();
        // TODO 如果有 BaseAuth 参数，base64 编码后转换成 headers
        if (request.isAuthSwitch()) {
            AuthorizationValue authorizationValue = new AuthorizationValue();
            authorizationValue.setType(HEADER);
            authorizationValue.setKeyName("Authorization");
            String authValue = "Basic " + Base64.getUrlEncoder().encodeToString((request.getAuthUsername() + ":" + request.getAuthPassword()).getBytes());
            authorizationValue.setValue(authValue);
            auths.add(authorizationValue);
        }

        // 设置 headers
        if (StringUtils.isNotBlank(request.getSwaggerToken())) {
            String[] tokenRows = StringUtils.split(request.getSwaggerToken(), StringUtils.LF);
            for (String row : tokenRows) {
                String[] tokenArr = StringUtils.split(row, ":");
                if (tokenArr.length == 2) {
                    AuthorizationValue authorizationValue = new AuthorizationValue();
                    authorizationValue.setType(HEADER);
                    authorizationValue.setKeyName(tokenArr[0]);
                    authorizationValue.setValue(tokenArr[1]);
                    auths.add(authorizationValue);
                }
            }
        }

        return CollectionUtil.size(auths) == 0 ? null : auths;
    }




}
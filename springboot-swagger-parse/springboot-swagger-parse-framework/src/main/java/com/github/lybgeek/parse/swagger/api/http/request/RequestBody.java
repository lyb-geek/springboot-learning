package com.github.lybgeek.parse.swagger.api.http.request;

import com.github.lybgeek.parse.swagger.api.http.metadata.body.binary.BinaryBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.FormDataBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.form.WWWFormBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.json.JsonBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.none.NoneBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.raw.RawBody;
import com.github.lybgeek.parse.swagger.api.http.metadata.body.xml.XmlBody;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


/**
 * @description:请求体
 *
 **/
@Data
public class RequestBody {
    /**
     * 当前选择的请求体类型
     * 可选值为 {@link BodyType}
     * 同时持久化多个类型的请求体
     */
    private String bodyType = BodyType.NONE.name();
    /**
     * None 请求体
     * 当 bodyType 为 NONE 时，使用该字段
     */
    private NoneBody noneBody;
    /**
     * form-data 请求体
     * 当 bodyType 为 FORM_DATA 时，使用该字段
     */
    private FormDataBody formDataBody;
    /**
     * x-www-form-urlencoded 请求体
     * 当 bodyType 为 WWW_FORM 时，使用该字段
     */
    private WWWFormBody wwwFormBody;
    /**
     * json 请求体
     * 当 bodyType 为 JSON 时，使用该字段
     */
    private JsonBody jsonBody;
    /**
     * xml 请求体
     * 当 bodyType 为 XML 时，使用该字段
     */
    private XmlBody xmlBody;
    /**
     * raw 请求体
     * 当 bodyType 为 RAW 时，使用该字段
     */
    private RawBody rawBody;
    /**
     * binary 请求体
     * 当 bodyType 为 BINARY 时，使用该字段
     */
    private BinaryBody binaryBody;

    /**
     * 请求体类型与请求体类的映射
     * 不需要传惨
     */
    private static Map<BodyType, Class> bodyTypeClassMap = new HashMap<>();

    static {
        bodyTypeClassMap.put(BodyType.NONE, NoneBody.class);
        bodyTypeClassMap.put(BodyType.FORM_DATA, FormDataBody.class);
        bodyTypeClassMap.put(BodyType.WWW_FORM, WWWFormBody.class);
        bodyTypeClassMap.put(BodyType.JSON, JsonBody.class);
        bodyTypeClassMap.put(BodyType.XML, XmlBody.class);
        bodyTypeClassMap.put(BodyType.RAW, RawBody.class);
        bodyTypeClassMap.put(BodyType.BINARY, BinaryBody.class);
    }

    public Class getBodyClassByType() {
        return bodyTypeClassMap.get(BodyType.valueOf(bodyType));
    }

    public Object getBodyDataByType() {
        Map<BodyType, Object> boadyDataMap = new HashMap<>();
        boadyDataMap.put(BodyType.NONE, noneBody);
        boadyDataMap.put(BodyType.FORM_DATA, formDataBody);
        boadyDataMap.put(BodyType.WWW_FORM, wwwFormBody);
        boadyDataMap.put(BodyType.JSON, jsonBody);
        boadyDataMap.put(BodyType.XML, xmlBody);
        boadyDataMap.put(BodyType.RAW, rawBody);
        boadyDataMap.put(BodyType.BINARY, binaryBody);
        return boadyDataMap.get(BodyType.valueOf(bodyType));
    }


    /**
     * 请求体类型
     */
    public enum BodyType {
        /**
         * 二进制文件
         */
        BINARY,
        /**
         * form-data
         */
        FORM_DATA,
        /**
         * none
         */
        NONE,
        /**
         * raw
         */
        RAW,
        /**
         * x-www-form-urlencoded
         */
        WWW_FORM,
        /**
         * xml
         */
        XML,
        /**
         * json
         */
        JSON
    }
}

package com.github.lybgeek.parse.swagger.export.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class SwaggerParams implements Serializable {
    //对应 API 请求参数名
    private String name;
    //参数值
    private String example;
    //参数类型，可选值为 path,header,query 等
    private String in;
    private String description;
    //是否是必填参数
    private boolean enable;
    private JsonNode schema;
}
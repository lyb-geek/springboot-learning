package com.github.lybgeek.parse.swagger.export.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SwaggerInfo implements Serializable {
    private String version;
    private String title;
    private String description;
    private String termsOfService;


    private String swaggerVersion = "3.0.2";




}
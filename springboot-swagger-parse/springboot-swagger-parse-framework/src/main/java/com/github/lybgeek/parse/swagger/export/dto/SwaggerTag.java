package com.github.lybgeek.parse.swagger.export.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SwaggerTag implements Serializable {
    private String name;
    private String description;
}
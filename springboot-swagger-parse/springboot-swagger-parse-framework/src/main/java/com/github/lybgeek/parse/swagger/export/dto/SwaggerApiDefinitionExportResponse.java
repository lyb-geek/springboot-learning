package com.github.lybgeek.parse.swagger.export.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SwaggerApiDefinitionExportResponse implements Serializable {

    private String openapi;
    private SwaggerInfo info;
    private JsonNode externalDocs;
    private List<String> servers;
    private List<SwaggerTag> tags;
    private JsonNode paths;
    private JsonNode components;
}
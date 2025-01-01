package com.github.lybgeek.parse.swagger.export.service;

import com.github.lybgeek.parse.swagger.export.dto.ApiDefinitionWithBlob;
import com.github.lybgeek.parse.swagger.export.dto.SwaggerInfo;

import java.util.List;
import java.util.Map;

public interface ExportParser<T> {
    T parse(List<ApiDefinitionWithBlob> list, SwaggerInfo swaggerInfo, Map<String, String> moduleMap) throws Exception;
}
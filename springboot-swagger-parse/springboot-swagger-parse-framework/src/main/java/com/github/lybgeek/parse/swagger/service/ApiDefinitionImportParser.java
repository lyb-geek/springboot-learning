package com.github.lybgeek.parse.swagger.service;

import com.github.lybgeek.parse.swagger.dto.ImportRequest;

import java.io.InputStream;

public interface ApiDefinitionImportParser<T> {

    /**
     * 解析导入
     *
     * @param source  导入文件流
     * @param request 导入的请求参数
     * @return 解析后的数据
     * @throws Exception
     */
    T parse(InputStream source, ImportRequest request) throws Exception;



}
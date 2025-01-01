package com.github.lybgeek.parse.swagger.api;


import com.github.lybgeek.parse.swagger.api.definition.ApiDefinitionDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiDefinitionImportParserResult {

    // 接口定义数据
    private List<ApiDefinitionDetail> data = new ArrayList<>();
}

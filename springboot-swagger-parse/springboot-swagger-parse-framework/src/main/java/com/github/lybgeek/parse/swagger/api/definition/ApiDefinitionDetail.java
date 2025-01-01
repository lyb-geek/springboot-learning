package com.github.lybgeek.parse.swagger.api.definition;

import com.github.lybgeek.parse.swagger.api.http.request.HttpRequest;
import com.github.lybgeek.parse.swagger.api.http.response.HttpResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiDefinitionDetail extends ApiDefinition {

    @ApiModelProperty(value = "请求内容")
    private HttpRequest request;

    @ApiModelProperty(value = "响应内容")
    private List<HttpResponse> response;

    @ApiModelProperty(value = "模块path")
    private String modulePath;

}

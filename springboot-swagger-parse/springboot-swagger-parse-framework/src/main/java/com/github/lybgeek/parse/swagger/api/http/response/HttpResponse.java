package com.github.lybgeek.parse.swagger.api.http.response;

import com.github.lybgeek.parse.swagger.api.http.metadata.header.Header;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class HttpResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一ID")
    private String id;

    @ApiModelProperty(value = "响应码")
    private String statusCode;

    @ApiModelProperty(value = "默认响应标识")
    private Boolean defaultFlag;
    
    @ApiModelProperty(value = "响应名称")
    private String name;

    @ApiModelProperty(value = "响应请求头")
    private List<Header> headers = new ArrayList<>();

    @ApiModelProperty(value = "响应请求体")
    private ResponseBody body = new ResponseBody();

}

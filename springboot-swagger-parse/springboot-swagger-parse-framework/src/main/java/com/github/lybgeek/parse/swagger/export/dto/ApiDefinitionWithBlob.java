package com.github.lybgeek.parse.swagger.export.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@ApiModel(value = "接口导出定义")
public class ApiDefinitionWithBlob extends ApiDefinitionBlob {

    @ApiModelProperty(value = "接口pk", required = true)
    private String id;

    @ApiModelProperty(value = "接口名称",required = true)
    private String name;

    @ApiModelProperty(value = "接口协议",required = true)
    private String protocol;

    @ApiModelProperty(value = "http协议类型post/get/其它协议则是协议名(mqtt)")
    private String method;

    @ApiModelProperty(value= "http协议路径/其它协议则为空")
    private String path;

    @ApiModelProperty(value = "接口状态/进行中/已完成", required = true)
    private String status;

    @ApiModelProperty(value = "自定义id")
    private Long num;

    @ApiModelProperty(value = "标签")
    private java.util.List<String> tags;

    @ApiModelProperty(value = "自定义排序", required = true)
    private Long pos;

    @ApiModelProperty(value = "项目fk", required = true)
    private String projectId;

    @ApiModelProperty(value = "模块fk", required = true)
    private String moduleId;

    @ApiModelProperty(value = "是否为最新版本 0:否，1:是", required = true)
    private Boolean latest;

    @ApiModelProperty(value = "版本fk", required = true)
    private String versionId;

    @ApiModelProperty(value = "版本引用fk", required = true)
    private String refId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "删除人")
    private String deleteUser;

    @ApiModelProperty(value = "删除时间")
    private Long deleteTime;

    @ApiModelProperty(value = "删除状态", required = true)
    private Boolean deleted;

}

package com.github.lybgeek.parse.swagger.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ImportRequest {
    private String id;
    private String name;
    @ApiModelProperty(value = "导入的模块id")
    private String moduleId;
    @ApiModelProperty(value = "导入的项目id")
    private String projectId;
    @ApiModelProperty(value = "导入的swagger地址")
    private String swaggerUrl;
    @ApiModelProperty(value = "导入的swagger文件地址")
    private String swaggerFilePath;
    @ApiModelProperty(value = "导入的swagger token")
    private String swaggerToken;
    @ApiModelProperty(value = "如果是定时任务的时候 需要传入创建人id")
    private String userId;
    private String versionId; // 新导入选择的版本
    private String updateVersionId; // 覆盖导入已存在的接口选择的版本
    private String defaultVersion;
    @ApiModelProperty(value = "三方平台  暂定 Swagger3  Postman Har")
    private String platform;
    @ApiModelProperty(value = "导入的类型  暂定  API  Schedule")
    private String type;

    @ApiModelProperty(value = "协议")
    private String protocol = "HTTP";
    @ApiModelProperty(value = "Basic Auth认证用户名")
    private String authUsername;
    @ApiModelProperty(value = "Basic Auth认证密码")
    private String authPassword;
    @ApiModelProperty(value = "唯一标识  默认是Method & Path  后续估计会补充")
    private String uniquelyIdentifies = "Method & Path";
    @ApiModelProperty(value = "定时任务的资源id")
    private String resourceId;

    @ApiModelProperty(value = "是否覆盖模块")
    private boolean coverModule;
    @ApiModelProperty(value = "是否同步导入用例")
    private boolean syncCase;
    @ApiModelProperty(value = "是否同步导入Mock")
    private boolean syncMock;
    @ApiModelProperty(value = "是否覆盖数据")
    private boolean coverData;
    @ApiModelProperty(value = "是否开启Basic Auth认证")
    private boolean authSwitch;

    @ApiModelProperty(value = "操作人")
    private String operator;
}

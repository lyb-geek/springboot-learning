package com.github.lybgeek.parse.swagger.api.definition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@ApiModel(value = "接口定义")
public class ApiDefinition implements Serializable {

    @ApiModelProperty(value = "接口pk")
    private String id;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "接口协议")
    private String protocol;

    @ApiModelProperty(value = "http协议类型post/get/其它协议则是协议名(mqtt)")
    private String method;

    @ApiModelProperty(value = "http协议路径/其它协议则为空")
    private String path;

    @ApiModelProperty(value = "接口状态/进行中/已完成")
    private String status;

    @ApiModelProperty(value = "自定义id")
    private Long num;

    @ApiModelProperty(value = "标签")
    private List<String> tags;

    @ApiModelProperty(value = "自定义排序")
    private Long pos;

    @ApiModelProperty(value = "项目fk")
    private String projectId;

    @ApiModelProperty(value = "模块fk")
    private String moduleId;

    @ApiModelProperty(value = "是否为最新版本 0:否，1:是")
    private Boolean latest;

    @ApiModelProperty(value = "版本fk")
    private String versionId;

    @ApiModelProperty(value = "版本引用fk")
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

    @ApiModelProperty(value = "删除状态")
    private Boolean deleted;

    private static final long serialVersionUID = 1L;

    public enum Column {
        id("id", "id", "VARCHAR", false),
        name("name", "name", "VARCHAR", true),
        protocol("protocol", "protocol", "VARCHAR", false),
        method("method", "method", "VARCHAR", true),
        path("path", "path", "VARCHAR", true),
        status("status", "status", "VARCHAR", true),
        num("num", "num", "BIGINT", false),
        tags("tags", "tags", "VARCHAR", false),
        pos("pos", "pos", "BIGINT", false),
        projectId("project_id", "projectId", "VARCHAR", false),
        moduleId("module_id", "moduleId", "VARCHAR", false),
        latest("latest", "latest", "BIT", false),
        versionId("version_id", "versionId", "VARCHAR", false),
        refId("ref_id", "refId", "VARCHAR", false),
        description("description", "description", "VARCHAR", false),
        createTime("create_time", "createTime", "BIGINT", false),
        createUser("create_user", "createUser", "VARCHAR", false),
        updateTime("update_time", "updateTime", "BIGINT", false),
        updateUser("update_user", "updateUser", "VARCHAR", false),
        deleteUser("delete_user", "deleteUser", "VARCHAR", false),
        deleteTime("delete_time", "deleteTime", "BIGINT", false),
        deleted("deleted", "deleted", "BIT", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}
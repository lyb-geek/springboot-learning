package com.github.lybgeek.mybatisplus.common.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 * 
 */
@Getter
@Setter
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /** 创建者 */
 @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /** 创建者id */
    @TableField(fill = FieldFill.INSERT)
    private Long createdById;

    /** 更新者 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /** 更新者id */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long lastUpdatedById;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;

    /** 版本号 */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer objectVersionNumber;

    /** appId */
    private Long appId;

    /** 租户id */
    private Long tenantId;

    /**
     * 删除标记
     */
    @TableLogic(value = "0", delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteFlag;
}

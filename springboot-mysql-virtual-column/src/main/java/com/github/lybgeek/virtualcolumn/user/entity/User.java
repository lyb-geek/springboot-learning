package com.github.lybgeek.virtualcolumn.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user_json")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    private String userInfo;

    private Date createTime;

    @TableField(value = "v_user_name",insertStrategy = FieldStrategy.NEVER,updateStrategy = FieldStrategy.NEVER)
    private String username;

    @TableField(value = "v_date_month",insertStrategy = FieldStrategy.NEVER,updateStrategy = FieldStrategy.NEVER)
    private String month;


    public static final String USER_INFO = "user_info";
    public static final String CREATE_TIME = "create_time";
    public static final String USER_NAME = "v_user_name";
    public static final String MONTH = "v_date_month";




}

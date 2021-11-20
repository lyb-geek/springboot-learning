package com.github.lybgeek.mybatismate.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis.mate.annotation.FieldSensitive;
import mybatis.mate.strategy.SensitiveType;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @FieldSensitive(type = "testStrategy")
    private String username;

    @FieldSensitive(type = SensitiveType.password)
    private String password;

    @FieldSensitive(type = SensitiveType.chineseName)
    private String fullname;

    @FieldSensitive(type = SensitiveType.mobile)
    private String mobile;

    @FieldSensitive(type = SensitiveType.email)
    private String email;


}

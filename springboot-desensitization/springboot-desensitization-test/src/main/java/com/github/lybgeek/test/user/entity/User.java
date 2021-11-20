package com.github.lybgeek.test.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.github.lybgeek.desensitization.annotation.Sensitive;
import com.github.lybgeek.desensitization.enums.DesensitizedType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    @Sensitive(strategy = DesensitizedType.PASSWORD)
    private String password;

    @Sensitive(strategy = DesensitizedType.CHINESE_NAME)
    private String fullname;

    @Sensitive(strategy = DesensitizedType.MOBILE_PHONE)
    private String mobile;

    @Sensitive(strategy = DesensitizedType.EMAIL)
    private String email;


}

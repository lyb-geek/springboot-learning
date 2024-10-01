package com.github.lybgeek.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
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

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 全名
     */
    private String fullname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;


}

package com.github.lybgeek.mybatismate.user.dto;



import lombok.*;
import mybatis.mate.annotation.FieldSensitive;
import mybatis.mate.strategy.SensitiveType;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

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

    private String remark;
}

package com.github.lybgeek.user.dto;


import com.github.lybgeek.common.validate.annotation.EnumValid;
import com.github.lybgeek.common.validate.group.Add;
import com.github.lybgeek.common.validate.group.Delete;
import com.github.lybgeek.common.validate.group.Update;
import com.github.lybgeek.user.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class UserDTO implements Serializable {

  @NotNull(message = "编号不能为空",groups = {Update.class, Delete.class})
  @ApiModelProperty(value = "编号",name = "id",example = "1")
  private Long id;

  @NotBlank(message = "用户名不能为空",groups = {Add.class})
  @ApiModelProperty(value = "用户名",name = "userName",example = "zhangsan")
  private String userName;

  @NotBlank(message = "姓名不能为空",groups = {Add.class})
  @ApiModelProperty(value = "姓名",name = "realName",example = "张三")
  private String realName;

  @NotBlank(message = "密码不能为空",groups = {Add.class})
  @Size(max=32,min=6,message = "密码长度要在6-32之间",groups = {Add.class})
  @ApiModelProperty(value = "密码",name = "password",example = "123456")
  private String password;

  @NotNull(message = "性别不能为空",groups = {Add.class})
  @ApiModelProperty(value = "性别",name = "gender",example = "1")
  @EnumValid(target = Gender.class, message = "性别取值必须为0或者1",groups = {Add.class,Update.class})
  private Integer gender;

  @ApiModelProperty(value = "邮箱",name = "email",example = "zhangsan@qq.com")
  @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式",groups = {Add.class,Update.class})
  private String email;



}

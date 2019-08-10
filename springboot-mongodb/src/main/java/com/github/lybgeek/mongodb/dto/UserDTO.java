package com.github.lybgeek.mongodb.dto;

import com.github.lybgeek.mongodb.common.validation.enu.annotation.EnumValid;
import com.github.lybgeek.mongodb.enu.Gender;
import com.github.lybgeek.mongodb.model.Address;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO  implements Serializable {

  private Long id;

  @NotNull(message = "用户名不能为空")
  private String userName;

  @NotNull(message = "姓名不能为空")
  private String realName;

  @NotNull(message = "密码不能为空")
  @Size(max=32,min=6,message = "密码长度要在6-32之间")
  private String password;

  @NotNull(message = "性别不能为空")
  @EnumValid(target = Gender.class, message = "性别取值必须为0或者1")
  private Integer gender;

  @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式")
  private String email;

  private Address address;

  private String validateRollBack;

}

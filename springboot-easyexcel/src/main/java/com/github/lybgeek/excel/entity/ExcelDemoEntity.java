package com.github.lybgeek.excel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.lybgeek.excel.converter.ExcelDemoEntityStatusConverter;
import com.github.lybgeek.validator.group.AddGroup;
import com.github.lybgeek.validator.group.ExcelGroup;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDemoEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @ExcelProperty(value="用户ID")
  private Long userId;

  /**
   * 用户名
   */
  @NotBlank(message="用户名不能为空",groups = ExcelGroup.class)
  @ExcelProperty(value="用户名")
  private String username;

  /**
   * 密码
   */
  @NotBlank(message="密码不能为空", groups = AddGroup.class)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ExcelIgnore
  private String password;


  /**
   * 邮箱
   */
  @NotBlank(message="邮箱不能为空",groups = ExcelGroup.class)
  @Email(message="邮箱格式不正确",groups = ExcelGroup.class)
  @ExcelProperty(value = "邮箱")
  private String email;

  /**
   * 手机号
   */
  @ExcelProperty(value="手机号")
  private String mobile;

  /**
   * 状态  0：禁用   1：正常
   */
  @ExcelProperty(value="状态",converter = ExcelDemoEntityStatusConverter.class)
  private Integer status;


  /**
   * 创建时间
   */
  @ExcelProperty(value="创建时间")
  private Date createTime;


  /**
   * 部门名称
   */
  @NotNull(message="部门不能为空",groups = ExcelGroup.class)
  @ExcelProperty(value="部门名称")
  private String deptName;

}

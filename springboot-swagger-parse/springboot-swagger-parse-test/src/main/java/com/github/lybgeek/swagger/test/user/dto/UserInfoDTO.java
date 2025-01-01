package com.github.lybgeek.swagger.test.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@ApiModel(value="userInfoDTO", description="用户新增/编辑/详情 dto")
public class UserInfoDTO implements Serializable {

    @ApiModelProperty(value = "id",name = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名", name = "userName")
    private String userName;

    @ApiModelProperty(value = "昵称", name = "userAlias")
    private String userAlias;

    @ApiModelProperty(value = "身份证号", name = "cardNumber")
    private String cardNumber;


}

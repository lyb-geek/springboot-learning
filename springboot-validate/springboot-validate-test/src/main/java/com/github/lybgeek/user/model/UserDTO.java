package com.github.lybgeek.user.model;

import com.github.lybgeek.user.service.MobileCheckService;
import com.github.lybgeek.user.service.UserService;
import com.github.lybgeek.user.service.impl.UserServiceImpl;
import com.github.lybgeek.validate.constraint.anotation.Unique;
import com.github.lybgeek.validate.group.CrudValidate;
import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @NotNull(message = "{message.id.not.empty}",groups = {CrudValidate.Update.class})
    private Long id;

    @NotEmpty(message = "{message.username.not.empty}")
    @Unique(message = "{message.username.unique}",checkUniqueBeanClass = UserService.class)
    private String username;

    @NotEmpty(message = "{message.password.not.empty}")
    @Size(min = 6,max = 32,message = "{message.password.length}")
    private String password;

    @Email(message = "{message.email.format.error}")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "{message.mobile.format.error}")
    @Unique(message = "{message.mobile.unique}",checkUniqueBeanClass = MobileCheckService.class,checkField = "mobile")
    private String mobile;
}

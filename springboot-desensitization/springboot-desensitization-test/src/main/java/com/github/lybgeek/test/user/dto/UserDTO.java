package com.github.lybgeek.test.user.dto;


import com.github.lybgeek.desensitization.annotation.Sensitive;
import com.github.lybgeek.desensitization.enums.DesensitizedType;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

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

    @Sensitive(useDFA = true,dfaReplaceChar = "#",dfaReplaceCharRepeatCount = 3)
    private String remark;
}

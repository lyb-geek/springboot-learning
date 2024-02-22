package com.github.lybgeek.test.user.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Linyb
 * @date : 2024/2/11 22:03
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterEvent {

    private String userName;
    private String password;
    private String mobile;
}

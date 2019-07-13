package com.github.lybgeek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcConfigDTO {
    private String driverClassName;

    private String url;

    private String username;

    private String password;
}

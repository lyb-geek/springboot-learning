package com.github.lybgeek.pipeline.test.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String username;

    private String fullname;

    private Integer age;

    private String email;

    private String mobile;

    private String password;

}

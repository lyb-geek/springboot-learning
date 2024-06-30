package com.github.lybgeek.el.test.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String address;

    private String email;

    private String userType;
}

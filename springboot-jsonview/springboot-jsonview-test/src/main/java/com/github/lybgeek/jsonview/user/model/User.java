package com.github.lybgeek.jsonview.user.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.github.lybgeek.jsonview.support.PublicJsonView;
import com.github.lybgeek.jsonview.user.roleview.AdminJsonView;
import com.github.lybgeek.jsonview.user.roleview.UserJsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @JsonView(PublicJsonView.class)
    private String username;

    @JsonView(PublicJsonView.class)
    private String fullname;

    @JsonView(PublicJsonView.class)
    private Integer age;

    @JsonView({UserJsonView.class,AdminJsonView.class})
    private String email;

    @JsonView(AdminJsonView.class)
    private String phone;

    @JsonView(PublicJsonView.class)
    private Address address;



}

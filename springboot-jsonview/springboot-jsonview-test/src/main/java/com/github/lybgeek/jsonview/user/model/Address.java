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
public class Address {

    @JsonView({UserJsonView.class, AdminJsonView.class})
    private String streetName;

    @JsonView(PublicJsonView.class)
    private String city;

    @JsonView(PublicJsonView.class)
    private String country;

    @JsonView(AdminJsonView.class)
    private String fullAddress;
}

package com.github.lybgeek.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {
    @NotNull(message = "id不能为空")
    private Long id;
    private String name;
    private String job;
    private String sex;
}

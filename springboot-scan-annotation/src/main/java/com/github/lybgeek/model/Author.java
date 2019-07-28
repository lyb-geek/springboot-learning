package com.github.lybgeek.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Builder
@Entity
@Table(name = "author")
public class Author extends BaseEntity implements Serializable {
    @NotNull(message = "名字不能为空")
    private String name;
    @NotNull(message = "职业不能为空")
    private String job;
    @NotNull(message = "性别不能为空")
    private String sex;



}

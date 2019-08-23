package com.github.lybgeek.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {
    public static final int success = 0;
    public static final int fail = 1;
    private int status = success;
    private String message = "success";
    private T data;


}

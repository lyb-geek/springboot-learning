package com.github.lybgeek.mock.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcResponse<T> {

    private String msg;

    private int code;

    private T data;
}

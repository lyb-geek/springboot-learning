package com.github.lybgeek.mock.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RpcRequest {

    private Long requestId;

    private Long userId;
}

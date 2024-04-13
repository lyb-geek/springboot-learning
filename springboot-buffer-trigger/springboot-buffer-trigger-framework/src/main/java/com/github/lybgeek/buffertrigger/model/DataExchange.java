package com.github.lybgeek.buffertrigger.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CompletableFuture;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataExchange<T,R> {

    private String bizNo;
    private T request;
    private CompletableFuture<Result<R>> response;
}

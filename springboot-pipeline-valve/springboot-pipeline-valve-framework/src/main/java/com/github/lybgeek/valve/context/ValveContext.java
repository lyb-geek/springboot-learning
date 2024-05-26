package com.github.lybgeek.valve.context;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValveContext {

    private Map<String,Object> request = new ConcurrentHashMap<>();

    private String requestId;

    private CompletableFuture response;
}

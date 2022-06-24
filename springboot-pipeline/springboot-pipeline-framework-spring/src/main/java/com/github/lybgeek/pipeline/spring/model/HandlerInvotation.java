package com.github.lybgeek.pipeline.spring.model;


import com.github.lybgeek.pipeline.handler.AbstactChannelHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandlerInvotation {

    private AbstactChannelHandler handler;

    private String consumePipelinesMethod;

    private Class[] args;

    private int order;
}

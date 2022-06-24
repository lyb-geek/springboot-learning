package com.github.lybgeek.pipeline.spring.shema.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineConfig {

    private Class consumePipelinesService;

    private String consumePipelinesMethod;

    private Class[] args;

    private List<PipelineHandlerConfig> pipelineChain;
}

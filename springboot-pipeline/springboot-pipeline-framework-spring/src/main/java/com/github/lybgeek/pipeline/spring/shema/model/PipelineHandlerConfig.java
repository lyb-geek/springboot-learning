package com.github.lybgeek.pipeline.spring.shema.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineHandlerConfig {

    private int order;

    private Class PipelineClass;
}

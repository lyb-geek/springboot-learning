package com.github.lybgeek.pipeline.spring.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PipelineDefinition {

    public static final String PREFIX = "lybgeek_pipeline_";

    private String comsumePipelineName;

    private List<String> pipelineClassNames;
}

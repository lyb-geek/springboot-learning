package com.github.lybgeek.pipeline.spring.properties;


import com.github.lybgeek.pipeline.spring.model.PipelineDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = PipelineDefinitionProperties.PREFIX)
public class PipelineDefinitionProperties {

    public final static String PREFIX = "lybgeek.pipeline";

    private List<PipelineDefinition> chain;
}

package com.github.lybgeek.aop.core.model;


import com.github.lybgeek.aop.core.locator.enums.OperateEventEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProxyMetaDefinitionChangeEntity {

    private OperateEventEnum operateEventEnum;

    private ProxyMetaDefinition definition;
}

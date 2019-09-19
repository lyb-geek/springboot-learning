package com.github.lybgeek.common.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsIndex {

  private String indexName;

  @Builder.Default
  private String type = "_doc";

  @Builder.Default
  private int shards = 5;

  @Builder.Default
  private int replicas  = 1;

}

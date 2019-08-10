package com.github.lybgeek.mongodb.common.id.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "sequence_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SequenceId {

  /**
   * 主键
   */
  @Id
  private String id;

  /**
   * 序列值
   */
  @Field("seqId")
  private long seqId;

  /**
   * 集合名称
   */
  @Field("collName")
  private String collName;

}

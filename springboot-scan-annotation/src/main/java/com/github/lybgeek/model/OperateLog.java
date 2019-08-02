package com.github.lybgeek.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
@Table(name = "operate_log")
public class OperateLog extends BaseEntity implements Serializable {

  private String method;
  @Column(length = 5000)
  private String paramsJson;
  @Column(length = 5000)
  private String resultJson;
  private String remark;


}

package com.github.lybgeek.orm.jpa.model;

import com.github.lybgeek.orm.common.model.BaseEntity;
import com.github.lybgeek.orm.jpa.common.annotation.IgnoreNullValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="order_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@IgnoreNullValue
public class OrderLog extends BaseEntity {

  @Column(name="order_id")
  private Long orderId;

  @Column(name="order_content",length = 2000)
  private String orderContent;

  @Column(name="order_name")
  private String orderName;

}

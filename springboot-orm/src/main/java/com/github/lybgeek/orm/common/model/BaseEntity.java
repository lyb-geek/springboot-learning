package com.github.lybgeek.orm.common.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.lybgeek.orm.mybatis.annotation.CreateDate;
import com.github.lybgeek.orm.mybatis.annotation.UpdateDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @TableId(type= IdType.AUTO)
  private Long id;

  @CreationTimestamp
  @Column(name="create_date",updatable = false)
  @CreateDate
  private Date createDate;

  @UpdateTimestamp
  @Column(name="update_date")
  @UpdateDate
  private Date updateDate;

}

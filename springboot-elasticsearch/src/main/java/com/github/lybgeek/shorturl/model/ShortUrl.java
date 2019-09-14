package com.github.lybgeek.shorturl.model;

import com.github.lybgeek.common.jpa.annotation.IgnoreNullValue;
import com.github.lybgeek.common.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_short_url")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@IgnoreNullValue
public class ShortUrl extends BaseEntity implements Serializable {

    /**
     * 长链接
     */
    @Column(name="long_url",nullable = false,columnDefinition="varchar(500) COMMENT '长链接'")
    private String longUrl;


    /**
     * 链接名称
     */
    @Column(name="url_name",columnDefinition="varchar(100) COMMENT '链接名称'")
    private String urlName;

    /**
     * 描述
     */
    @Column(name="remark",columnDefinition="varchar(1000) COMMENT '描述'")
    private String remark;
}

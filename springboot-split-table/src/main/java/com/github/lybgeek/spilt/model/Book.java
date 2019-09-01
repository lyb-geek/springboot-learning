package com.github.lybgeek.spilt.model;

import com.github.lybgeek.common.model.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class Book extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String bookName;

    private String author;

    private String description;

    private BigDecimal price;

    private Integer stock;


}

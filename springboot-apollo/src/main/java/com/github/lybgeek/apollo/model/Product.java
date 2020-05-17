package com.github.lybgeek.apollo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties(prefix = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RefreshScope
public class Product {

    private Long id;

    private String productName;

    private BigDecimal price;

}

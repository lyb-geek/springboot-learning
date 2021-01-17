package com.github.lybgeek.mybatisplus.tenant.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "mp.tenant")
public class MyBatisPlusTenantProperties {

    /** tenantid enabled; default false*/
    private boolean enabled;

    /**skips fill tenantid with these tables;*/
    private List<String> skipFillTenantIdTables = new ArrayList<>();
}

package com.github.lybgeek.config.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshProperty implements Serializable {

    private Set<String> refreshKeys;

    private Map<String,Object> newConfigProperties;

}

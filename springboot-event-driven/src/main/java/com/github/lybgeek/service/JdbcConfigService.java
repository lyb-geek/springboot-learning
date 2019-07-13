package com.github.lybgeek.service;

import com.github.lybgeek.dto.JdbcConfigDTO;

public interface JdbcConfigService {

   String refreshJdbcConfig(JdbcConfigDTO jdbcConfigDTO);

}

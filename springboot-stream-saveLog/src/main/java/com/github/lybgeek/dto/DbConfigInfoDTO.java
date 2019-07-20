package com.github.lybgeek.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbConfigInfoDTO implements Serializable {

  private String driverClassName;

  private String url;

  private String username;

  private String password;

  private String filters;

  private Integer initialSize;

  private Integer maxActive;

  private Integer maxWait;

  private Integer timeBetweenEvictionRunsMillis;

  private Integer minEvictableIdleTimeMillis;

  private String validationQuery;

  private boolean testWhileIdle;

  private boolean testOnBorrow;

  private boolean testOnReturn;

}

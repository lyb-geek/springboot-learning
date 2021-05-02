package com.github.lybgeek.logaop.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceLog {

    public static final String SUCEESS = "0";

    public static final String FAIL = "1";

    private Long id;

    private String serviceName;

    private String methodName;

    private String reqArgs;

    private String respResult;

    private long costTime;

    private String status;

    private Date createTime;


}

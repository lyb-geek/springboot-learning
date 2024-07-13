package com.github.lybgeek.kafka.model;


import com.github.lybgeek.kafka.enums.HttpMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParamRequest implements Serializable {

    private String requestParamJson;

    private String requestUrl;

    private HttpMethodEnum httpMethod;

    private Map<String,String> metadata;



}

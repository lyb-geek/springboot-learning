package com.github.lybgeek.sms.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsResponse<T> implements Serializable {

     private Map<String,Object> metaDatas;

     private String code;

     private String message;

     private boolean success;

     private T result;
}

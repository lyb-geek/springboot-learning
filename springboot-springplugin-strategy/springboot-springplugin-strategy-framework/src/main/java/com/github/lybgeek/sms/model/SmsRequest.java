package com.github.lybgeek.sms.model;


import com.github.lybgeek.sms.enums.SmsType;
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
public class SmsRequest implements Serializable {

    private Map<String,Object> metaDatas;

    private String to;

    private String message;

    private SmsType smsType;


}

package com.github.lybgeek.event.condition.sms.event;


import com.github.lybgeek.event.condition.sms.enums.SmsEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsEvent {

    private String phone;

    private String content;

    private SmsEnums smsEnums;
}

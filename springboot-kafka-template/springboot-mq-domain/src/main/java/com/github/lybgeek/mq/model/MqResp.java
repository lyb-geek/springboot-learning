package com.github.lybgeek.mq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MqResp implements Serializable {
    public static final String SUCCESS = "0";
    public static final String FAIL = "500";


    private String status;

    private boolean isOk;

    private String body;


    public static MqResp error(String message) {
        MqResp response = new MqResp();
        response.setStatus("500");
        response.setBody(message);
        return response;
    }

    public static MqResp error(String message, String status) {
        MqResp response = new MqResp();
        response.setStatus(status);
        response.setBody(message);
        return response;
    }

}

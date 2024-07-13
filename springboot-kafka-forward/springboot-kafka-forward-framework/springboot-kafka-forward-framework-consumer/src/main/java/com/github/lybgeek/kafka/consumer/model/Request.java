package com.github.lybgeek.kafka.consumer.model;


import com.github.lybgeek.kafka.model.ParamRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    private String url;
    private ParamRequest paramRequest;

}

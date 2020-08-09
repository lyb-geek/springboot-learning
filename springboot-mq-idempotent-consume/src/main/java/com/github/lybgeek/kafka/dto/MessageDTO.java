package com.github.lybgeek.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO<T> implements Serializable {

    private String messageId;


    private T data;
}

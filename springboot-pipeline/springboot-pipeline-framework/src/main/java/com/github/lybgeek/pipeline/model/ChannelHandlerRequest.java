package com.github.lybgeek.pipeline.model;

import lombok.Builder;


@Builder
public class ChannelHandlerRequest<T> {

    private String requestId;

    private T params;


    public String getRequestId() {
       return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }


}

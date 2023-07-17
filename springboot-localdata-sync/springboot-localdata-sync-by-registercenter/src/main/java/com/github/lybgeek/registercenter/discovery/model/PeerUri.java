package com.github.lybgeek.registercenter.discovery.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeerUri {

    private String host;

    private String ip;

    private int port;

    private String uri;

    private String httpUrl;

}

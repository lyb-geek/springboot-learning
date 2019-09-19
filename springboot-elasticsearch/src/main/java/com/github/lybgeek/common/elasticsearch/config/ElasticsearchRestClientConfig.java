package com.github.lybgeek.common.elasticsearch.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch.rest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticsearchRestClientConfig {

  private static final int ES_IPADDRS_LENGTH = 2;
  private static final String HTTP_SCHEME = "http";


  private List<String> ipAddrs = new ArrayList<>();


  @Bean
  public RestClientBuilder restClientBuilder() {
    HttpHost[] hosts = ipAddrs.stream()
        .map(this::makeHttpHost)
        .filter(Objects::nonNull)
        .toArray(HttpHost[]::new);
    return RestClient.builder(hosts);
  }

  @Bean(name = "highLevelClient")
  public RestHighLevelClient highLevelClient(RestClientBuilder restClientBuilder) {
    return new RestHighLevelClient(restClientBuilder);
  }



  private HttpHost makeHttpHost(String s) {
    assert StringUtils.isNotEmpty(s);
    String[] address = s.split(":");
    if (address.length == ES_IPADDRS_LENGTH) {
      String ip = address[0];
      int port = Integer.parseInt(address[1]);
      return new HttpHost(ip, port, HTTP_SCHEME);
    }

      return null;

  }



}

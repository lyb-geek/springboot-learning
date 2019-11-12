package com.github.lybgeek.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class HttpConterHelper {

  private final Counter counter;

  public HttpConterHelper(MeterRegistry registry) {
    this.counter = registry.counter("custom_api_http_requests_total");
  }

  public void count() {
    this.counter.increment();
  }

}

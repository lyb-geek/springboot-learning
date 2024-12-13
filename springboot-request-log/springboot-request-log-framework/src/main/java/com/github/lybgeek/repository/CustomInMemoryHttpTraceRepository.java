package com.github.lybgeek.repository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;

import java.util.List;

@Slf4j
public class CustomInMemoryHttpTraceRepository extends InMemoryHttpTraceRepository {

    @Override
    public List<HttpTrace> findAll() {
        log.info("CustomInMemoryHttpTraceRepository findAll");
        return super.findAll();
    }

    @Override
    public void add(HttpTrace trace) {
        log.info("CustomInMemoryHttpTraceRepository add");
        super.add(trace);
    }
}

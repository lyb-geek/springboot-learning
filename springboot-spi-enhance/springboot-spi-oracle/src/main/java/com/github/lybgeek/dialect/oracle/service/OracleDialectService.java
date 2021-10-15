package com.github.lybgeek.dialect.oracle.service;


import org.springframework.stereotype.Service;

@Service
public class OracleDialectService {

    public String dialect() {
        return "oracle by spring";
    }
}

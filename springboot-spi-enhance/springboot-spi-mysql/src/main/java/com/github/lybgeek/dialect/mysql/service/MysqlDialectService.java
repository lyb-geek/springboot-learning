package com.github.lybgeek.dialect.mysql.service;


import org.springframework.stereotype.Service;

@Service
public class MysqlDialectService {

    public String dialect() {
        return "mysql by spring";
    }
}

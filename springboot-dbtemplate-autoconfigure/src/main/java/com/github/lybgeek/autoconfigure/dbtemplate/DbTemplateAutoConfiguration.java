package com.github.lybgeek.autoconfigure.dbtemplate;

import com.github.lybgeek.db.template.DbTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DbTemplateAutoConfiguration {

    @Bean
    public DbTemplate dbTemplate(){
        DbTemplate dbTemplate = new DbTemplate(new QueryRunner());
        return dbTemplate;
    }

}

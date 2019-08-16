package com.github.lybgeek.autoconfigure.dbtemplate;

import com.github.lybgeek.db.template.DbTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DbTemplateAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value=DbTemplate.class)
    public DbTemplate dbTemplate(){
        DbTemplate dbTemplate = new DbTemplate(new QueryRunner());
        return dbTemplate;
    }

}

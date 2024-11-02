package com.github.lybgeek.togglz.env.factory.support;


import com.github.lybgeek.togglz.env.factory.StateRepositoryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.jdbc.JDBCStateRepository;

import javax.sql.DataSource;

import static com.github.lybgeek.togglz.env.constant.TogglzConstant.STORE_TYPE_JDBC;

@Slf4j
@RequiredArgsConstructor
public class JdbcStateRepositoryFactory implements StateRepositoryFactory {

    private final ObjectProvider<DataSource> dataSource;
    private final String tableName;

    @Override
    public StateRepository create() {
        return new JDBCStateRepository(dataSource.getIfAvailable(),tableName);
       
    }

    @Override
    public String supportType() {
        return STORE_TYPE_JDBC;
    }


}

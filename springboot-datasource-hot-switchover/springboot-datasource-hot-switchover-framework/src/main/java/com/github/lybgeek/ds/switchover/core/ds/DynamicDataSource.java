package com.github.lybgeek.ds.switchover.core.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final String DATASOURCE_KEY = "db";

    @Override
    protected Object determineCurrentLookupKey() {
        return DATASOURCE_KEY;
    }

    public DataSource getOriginalDetermineTargetDataSource(){
        return this.determineTargetDataSource();
    }
}

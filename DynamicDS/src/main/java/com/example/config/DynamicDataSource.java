package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static ThreadLocal<String> name = new ThreadLocal<>();
    public static final String DATASOURCE_READ_LOOKUP_KEY = "R";
    public static final String DATASOURCE_WRITE_LOOKUP_KEY = "W";

    @Autowired
    private DataSource dataSourceRead;
    @Autowired
    private DataSource dataSourceWrite;


    @Override
    protected Object determineCurrentLookupKey() {
        return name.get();
    }

    @Override
    public void afterPropertiesSet() {
        // 指定所有数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DATASOURCE_READ_LOOKUP_KEY, dataSourceRead);
        targetDataSources.put(DATASOURCE_WRITE_LOOKUP_KEY, dataSourceWrite);
        // 指定默认的数据源
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(dataSourceRead);

        super.afterPropertiesSet();
    }
}

package com.example.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource dataSourceRead() {
        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource dataSourceWrite() {
        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
        return DruidDataSourceBuilder.create().build();
    }
}

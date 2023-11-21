package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * @author ToTryEveryThing
 * @date 2023/11/21 9:45
 * @Description
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "db1")
    @Primary
    @ConfigurationProperties("spring.datasource.db1")
    public DruidDataSource dataSource1 () {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties("spring.datasource.db2")
    @QuartzDataSource
    public DruidDataSource dataSource2 () {
        return DruidDataSourceBuilder.create().build();
    }
}

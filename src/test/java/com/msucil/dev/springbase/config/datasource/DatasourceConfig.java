package com.msucil.dev.springbase.config.datasource;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatasourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final PostgreSQLContainer<CommonDatabaseContainer> container = CommonDatabaseContainer.getInstance();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());

        return dataSource;
    }
}

package com.msucil.dev.springbase.config.datasource;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;

@EnableJpaRepositories(basePackages = "com.msucil.dev.springbase.**")
@EntityScan("com.msucil.dev.springbase.domain.**")
@EnableTransactionManagement
@Configuration
public class JpaTestConfig {

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

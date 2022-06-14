package com.msucil.dev.springbase.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "com.msucil.dev.springbase.domain.**")
@EntityScan("com.msucil.dev.springbase.domain.**")
@EnableTransactionManagement
@TestConfiguration
public class JpaTestConfig {

}

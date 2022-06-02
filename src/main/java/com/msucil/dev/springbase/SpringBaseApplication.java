package com.msucil.dev.springbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringBaseApplication {

    private SpringBaseApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBaseApplication.class, args);
    }

}

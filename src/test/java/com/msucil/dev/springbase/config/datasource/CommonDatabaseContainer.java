package com.msucil.dev.springbase.config.datasource;

import org.testcontainers.containers.PostgreSQLContainer;

public class CommonDatabaseContainer extends PostgreSQLContainer<CommonDatabaseContainer> {

    private static final String IMAGE_VERSION = "postgres:10";

    private static CommonDatabaseContainer container;

    private CommonDatabaseContainer() {
        super(IMAGE_VERSION);
    }

    public static CommonDatabaseContainer getInstance() {
        if (null == container) {
            container = new CommonDatabaseContainer();
            container.start();
        }

        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}

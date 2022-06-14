package com.msucil.dev.springbase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.msucil.dev.springbase.config.datasource.DatasourceConfig;

@SpringBootTest
@Import(DatasourceConfig.class)
class SpringBaseApplicationTests {

    @Test
    void contextLoads() {
    }

}

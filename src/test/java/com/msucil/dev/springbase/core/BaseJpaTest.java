package com.msucil.dev.springbase.core;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.msucil.dev.springbase.config.datasource.DatasourceConfig;
import com.msucil.dev.springbase.config.jpa.JpaTestConfig;

@DataJpaTest
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@Import(value = {
    JpaTestConfig.class,
    DatasourceConfig.class
})
public abstract class BaseJpaTest {

}

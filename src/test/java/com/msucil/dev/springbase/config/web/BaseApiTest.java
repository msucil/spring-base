package com.msucil.dev.springbase.config.web;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;

import com.msucil.dev.springbase.config.security.SecurityConfig;

@AutoConfigureRestDocs
@Import(SecurityConfig.class)
public abstract class BaseApiTest {

    @Autowired
    protected MockMvcRestDocumentationConfigurer customizer;

    @BeforeEach
    public void setup() {
        customizer.operationPreprocessors().withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint());
    }


}

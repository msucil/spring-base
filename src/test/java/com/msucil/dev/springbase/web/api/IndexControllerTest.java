package com.msucil.dev.springbase.web.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.msucil.dev.springbase.config.web.BaseApiTest;

@WebMvcTest(IndexController.class)
class IndexControllerTest extends BaseApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    void testIndexShouldReturnWelcomeMessage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andDo(document("index"));
    }
}
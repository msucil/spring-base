package com.msucil.dev.springbase.web.api.v1.manage.user;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.msucil.dev.springbase.core.BaseApiTest;
import com.msucil.dev.springbase.domain.manage.user.UserCommandService;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseApiTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserQueryService userQueryService;

    @MockBean
    UserCommandService userCommandService;

    @Test
    public void testIndexShouldReturnUnauthorised() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/manage/users")
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void testIndexShouldReturnOkResponse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/manage/users"))
            .andExpect(status().isOk());
    }
}
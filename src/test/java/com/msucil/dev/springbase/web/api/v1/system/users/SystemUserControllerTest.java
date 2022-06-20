package com.msucil.dev.springbase.web.api.v1.system.users;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.msucil.dev.springbase.core.BaseApiTest;
import com.msucil.dev.springbase.domain.manage.user.User;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;
import com.msucil.dev.springbase.service.user.SystemUserService;

@WebMvcTest(SystemUserController.class)
class SystemUserControllerTest extends BaseApiTest {

    @MockBean
    private UserQueryService queryService;

    @MockBean
    private SystemUserService userService;

    @MockBean
    private SystemUserMapper userMapper;

    @Autowired
    MockMvc mvc;

    @Test
    void testCreateSystemUserShouldReturnValidationError() throws Exception {
        final var form = new SystemUserForm();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/system/users").content(
                objectMapper.writeValueAsString(form)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Validation Failed"))
            .andExpect(jsonPath("$.errorCount").value(6))
            .andExpect(jsonPath("$.path").value("/api/v1/system/users"))
            .andDo(document("system/users/create/validation-error"));
    }

    @Test
    void testCreateSystemUserShouldReturnForbidden() throws Exception {
        final var form = new SystemUserForm();
        form.setFullName("System User");
        form.setUsername("system");
        form.setEmail("system@sytem.com");
        form.setPassword("123456");
        form.setVerifyPassword("123456");

        when(queryService.canRegisterSystemUser()).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/system/users").content(
                objectMapper.writeValueAsString(form)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andDo(document("system/users/create/forbidden-error"));
    }

    @Test
    void testCreateSystemUserShouldSaveUser() throws Exception {
        final var form = new SystemUserForm();
        form.setFullName("System User");
        form.setUsername("system");
        form.setEmail("system@sytem.com");
        form.setPassword("123456");
        form.setVerifyPassword("123456");

        var u = new User();
        u.setId(1L);
        u.setFullName("System User");
        u.setUsername("system");
        u.setEmail("system@sytem.com");
        u.setPassword("123456");

        when(queryService.canRegisterSystemUser()).thenReturn(true);
        when(userService.create(any(User.class))).thenReturn(u);
        when(userMapper.mapSystemUserForm(any(SystemUserForm.class))).thenReturn(u);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/system/users").content(
                objectMapper.writeValueAsString(form)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.detail.id").value(1))
            .andDo(document("system/users/create/success"));

        var fCaptor = ArgumentCaptor.forClass(SystemUserForm.class);
        verify(userMapper, times(1)).mapSystemUserForm(fCaptor.capture());

        assertThat(fCaptor.getValue().getUsername(), equalTo(form.getUsername()));

        var uCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService, times(1)).create(uCaptor.capture());

        assertThat(uCaptor.getValue().getId(), equalTo(u.getId()));
    }
}
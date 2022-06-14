package com.msucil.dev.springbase.web.api.v1.manage.user;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.msucil.dev.springbase.core.BaseApiTest;
import com.msucil.dev.springbase.domain.manage.user.User;
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

    @Test
    @WithMockUser
    void testSaveShouldCreateUser() throws Exception {
        final var user = new User();
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setFullName("System");
        user.setPassword("Password");

        final var saved = new User();
        saved.setUsername("username");
        saved.setEmail("email@gmail.com");
        saved.setFullName("System");
        saved.setPassword("Password");
        saved.setId(1L);

        when(userCommandService.save(any(User.class))).thenReturn(saved);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/manage/users").content(
                objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.id").value("1"));

        final var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userCommandService, times(1)).save(userCaptor.capture());

        assertThat(userCaptor.getValue().getEmail(), equalTo(user.getEmail()));
        assertThat(userCaptor.getValue().getPassword(), equalTo(user.getPassword()));

//        https://reflectoring.io/spring-boot-web-controller-test/
    }

    @Test
    @WithMockUser
    void testCreateUserShouldReturnBadRequestOnValidationError() throws Exception {
        final var user = new User();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/manage/users").content(
                objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser
    void testUpdateUserShouldReturnBadRequestOnInvalidId() throws Exception {
        final var user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setFullName("System");
        user.setPassword("Password");

        when(userQueryService.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/manage/users/1")
                .content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        final var idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userQueryService, times(1)).findById(idCaptor.capture());

        assertThat(idCaptor.getValue(), equalTo(1L));
    }

    @Test
    @WithMockUser
    void testUpdateUserShouldReturnShouldUpdateUser() throws Exception {
        final var user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setFullName("System");
        user.setPassword("Password");

        when(userQueryService.findById(1L)).thenReturn(Optional.of(user));
        when(userCommandService.update(any(User.class))).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/manage/users/1")
                .content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));

        final var idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userQueryService, times(1)).findById(idCaptor.capture());

        assertThat(idCaptor.getValue(), equalTo(1L));

        final var userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userCommandService, times(1)).update(userCaptor.capture());

        assertThat(userCaptor.getValue().getEmail(), equalTo(user.getEmail()));
        assertThat(userCaptor.getValue().getPassword(), equalTo(user.getPassword()));
    }

    @Test
    @WithMockUser
    void testDeleteUserShouldReturnShouldUpdateUser() throws Exception {
        final var user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setEmail("email@gmail.com");
        user.setFullName("System");
        user.setPassword("Password");

        when(userQueryService.findById(1L)).thenReturn(Optional.of(user));
        when(userCommandService.update(any(User.class))).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/manage/users/1"))
            .andExpect(status().isOk()).andExpect(content().string("User deleted successfully"));
    }
}
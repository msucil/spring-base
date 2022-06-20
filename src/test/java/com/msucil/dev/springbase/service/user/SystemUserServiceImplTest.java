package com.msucil.dev.springbase.service.user;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msucil.dev.springbase.domain.manage.user.User;
import com.msucil.dev.springbase.domain.manage.user.UserCommandService;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;

@ExtendWith(MockitoExtension.class)
class SystemUserServiceImplTest {

    @Mock
    UserQueryService queryService;

    @Mock
    UserCommandService commandService;

    @InjectMocks
    SystemUserServiceImpl service;

    @Test
    void testCreateShouldReturnRunTimeException() {
        var u = new User();

        when(queryService.canRegisterSystemUser()).thenReturn(false);

        assertThrows(UserExistException.class, () -> service.create(u));

        verify(queryService, times(1)).canRegisterSystemUser();
    }

    @Test
    void testCreateShouldSaveUser() {
        var u = new User();
        u.setUsername("username");

        var s = new User();
        s.setId(1L);

        when(queryService.canRegisterSystemUser()).thenReturn(true);
        when(commandService.save(any(User.class))).thenReturn(s);

        var su = service.create(u);
        assertThat(su.getId(), equalTo(1L));

        var uCaptor = ArgumentCaptor.forClass(User.class);
        verify(commandService, times(1)).save(uCaptor.capture());

        assertThat(uCaptor.getValue().getUsername(), equalTo(u.getUsername()));

    }

}
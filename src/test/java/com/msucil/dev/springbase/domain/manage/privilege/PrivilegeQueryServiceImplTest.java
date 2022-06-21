package com.msucil.dev.springbase.domain.manage.privilege;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class PrivilegeQueryServiceImplTest {

    @Mock
    private PrivilegeRepository repository;

    @InjectMocks
    private PrivilegeQueryServiceImpl queryService;

    @Test
    void testFindAllShouldReturnRecords() {

        var r = new Privilege();
        r.setId(1L);
        r.setName(SystemPrivilege.SYSTEM_ADMIN);
        r.setCreatedAt(Instant.now());

        when(repository.findAll(Sort.by("name"))).thenReturn(List.of(r));

        var records = queryService.findAll();

        assertThat(records, hasSize(1));
        assertThat(records.get(0).getName(), equalTo(SystemPrivilege.SYSTEM_ADMIN));
        assertThat(records.get(0).getLabel(), equalTo(SystemPrivilege.SYSTEM_ADMIN.getLabel()));

        var captor = ArgumentCaptor.forClass(Sort.class);
        verify(repository, times(1)).findAll(captor.capture());

        assertThat(captor.getValue().isEmpty(), equalTo(false));

    }

    @Test
    void testFindByNameShouldReturnRecord() {
        var r = new Privilege();
        r.setId(1L);
        r.setName(SystemPrivilege.SYSTEM_ADMIN);
        r.setCreatedAt(Instant.now());

        when(repository.findPrivilegeByName(SystemPrivilege.SYSTEM_ADMIN)).thenReturn(
            Optional.of(r));

        var record = queryService.findByName(SystemPrivilege.SYSTEM_ADMIN);

        assertThat(record.isPresent(), equalTo(true));
        assertThat(record.get().getName(), equalTo(SystemPrivilege.SYSTEM_ADMIN));
        assertThat(record.get().getLabel(), equalTo(SystemPrivilege.SYSTEM_ADMIN.getLabel()));

        final var captor = ArgumentCaptor.forClass(SystemPrivilege.class);

        verify(repository, times(1)).findPrivilegeByName(captor.capture());
        assertThat(captor.getValue(), equalTo(SystemPrivilege.SYSTEM_ADMIN));
    }
}
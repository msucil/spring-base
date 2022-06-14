package com.msucil.dev.springbase.domain.manage.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.msucil.dev.springbase.core.BaseJpaTest;

class UserRepositoryTest extends BaseJpaTest {

    @Autowired
    UserRepository repository;

    @Test
    void testSaveShouldSaveUser() {
        var user = new User();
        user.setUsername("system");
        user.setFullName("System User");
        user.setPassword("password");
        user.setEmail("system@user.com");

        var saved = repository.save(user);

        assertThat(saved.getId(), greaterThan(0L));
        assertThat(saved.getUsername(), equalTo(user.getUsername()));
        assertThat(saved.isAccountNonExpired(), equalTo(true));
        assertThat(saved.isAccountNonLocked(), equalTo(true));
        assertThat(saved.getCredentialsExpiryDate(), nullValue());
        assertThat(saved.isCredentialsNonExpired(), equalTo(true));
        assertThat(saved.getFullName(), equalTo(user.getFullName()));
        assertThat(saved.getPassword(), equalTo(user.getPassword()));
        assertThat(saved.isPermanentDelete(), equalTo(false));
        assertThat(saved.getVersion(), equalTo(0));
    }

    @Test
    @DatabaseSetup("/data/user/users.xml")
    void testFindAllShouldReturnUsers() {
        var users = repository.findAll();

        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getEmail(), equalTo("email@email.com"));
    }

    @Test
    void testSaveShouldThrowValidationException() {
        var user = new User();
        var ex = assertThrows(ConstraintViolationException.class, () -> repository.save(user));
        assertThat(ex, notNullValue());
        assertThat(ex.getConstraintViolations(), hasSize(3));
    }

    @Test
    @DatabaseSetup("/data/user/users.xml")
    void testSaveShouldReturnDataAccessException() {
        var user = new User();
        user.setUsername("system");
        user.setFullName("System User");
        user.setPassword("password");
        user.setEmail("system@user.com");

        var ex = assertThrows(DataAccessException.class, () -> repository.save(user));

        assertThat(ex.getMessage(), notNullValue());
        assertThat(ex.getMessage(), containsString("ConstraintViolationException"));
    }

    @Test
    @DatabaseSetup("/data/user/users.xml")
    void testDeleteByIdShouldDelete(){
        var users = repository.findAll();

        repository.deleteById(users.get(0).getId());
        assertThat(repository.findAll(), hasSize(0));
    }
}
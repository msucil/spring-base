package com.msucil.dev.springbase.domain.manage.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    }
}
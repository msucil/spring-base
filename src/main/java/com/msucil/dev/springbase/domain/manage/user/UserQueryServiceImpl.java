package com.msucil.dev.springbase.domain.manage.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository repository;

    public UserQueryServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}

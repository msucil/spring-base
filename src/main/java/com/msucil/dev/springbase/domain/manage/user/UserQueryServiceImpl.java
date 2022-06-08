package com.msucil.dev.springbase.domain.manage.user;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}

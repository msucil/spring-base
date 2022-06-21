package com.msucil.dev.springbase.domain.manage.privilege;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeQueryServiceImpl implements PrivilegeQueryService {

    private final PrivilegeRepository repository;

    public PrivilegeQueryServiceImpl(PrivilegeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Privilege> findAll() {
        return repository.findAll(Sort.by("name"));
    }

    @Override
    public Optional<Privilege> findByName(SystemPrivilege systemPrivilege) {
        return repository.findPrivilegeByName(systemPrivilege);
    }
}

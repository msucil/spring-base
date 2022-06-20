package com.msucil.dev.springbase.domain.manage.user;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean canRegisterSystemUser();
}

package com.msucil.dev.springbase.domain.manage.privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeQueryService {

    List<Privilege> findAll();

    Optional<Privilege> findByName(SystemPrivilege systemPrivilege);
}

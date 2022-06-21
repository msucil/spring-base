package com.msucil.dev.springbase.domain.manage.privilege;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findPrivilegeByName(SystemPrivilege systemPrivilege);
}

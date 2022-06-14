package com.msucil.dev.springbase.domain.manage.user;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}

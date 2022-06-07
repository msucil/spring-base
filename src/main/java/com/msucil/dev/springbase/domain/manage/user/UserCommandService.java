package com.msucil.dev.springbase.domain.manage.user;

public interface UserCommandService {

    User save(User user);

    User update(User user);

    void delete(Long id);
}

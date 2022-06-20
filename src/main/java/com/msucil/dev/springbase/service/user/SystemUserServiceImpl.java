package com.msucil.dev.springbase.service.user;

import org.springframework.stereotype.Service;

import com.msucil.dev.springbase.domain.manage.user.User;
import com.msucil.dev.springbase.domain.manage.user.UserCommandService;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    private final UserCommandService commandService;
    private final UserQueryService queryService;

    public SystemUserServiceImpl(
        UserCommandService commandService,
        UserQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @Override
    public User create(User user) {
        if (!queryService.canRegisterSystemUser()) {
            throw new UserExistException("Can not create System User. Users already exists.");
        }

        return commandService.save(user);
    }
}

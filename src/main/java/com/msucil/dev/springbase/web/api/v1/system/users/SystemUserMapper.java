package com.msucil.dev.springbase.web.api.v1.system.users;

import org.mapstruct.Mapper;

import com.msucil.dev.springbase.domain.manage.user.User;

@Mapper(componentModel = "spring")
public interface SystemUserMapper {

    User mapSystemUserForm(SystemUserForm form);
}

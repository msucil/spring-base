package com.msucil.dev.springbase.web.api.v1.system.users;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.msucil.dev.springbase.core.web.ResponseDetail;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;
import com.msucil.dev.springbase.service.user.SystemUserService;

@RestController
@RequestMapping("/api/v1/system/users")
public class SystemUserController {

    private final UserQueryService queryService;
    private final SystemUserService userService;
    private final SystemUserMapper userMapper;

    public SystemUserController(
        UserQueryService queryService,
        SystemUserService userService,
        SystemUserMapper userMapper) {
        this.queryService = queryService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDetail> create(@Valid @RequestBody SystemUserForm form) {

        if (!queryService.canRegisterSystemUser()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Can not create System User. Users already exists.");
        }

        return ResponseEntity.ok(
            ResponseDetail.builder()
                .status(HttpStatus.OK.value())
                .detail(userService.create(userMapper.mapSystemUserForm(form)))
                .build());
    }

    @GetMapping("/can-register")
    public ResponseEntity<ResponseDetail> canRegisterSystemUser() {
        return ResponseEntity.ok(ResponseDetail.builder().status(HttpStatus.OK.value())
            .detail(queryService.canRegisterSystemUser())
            .build());
    }
}

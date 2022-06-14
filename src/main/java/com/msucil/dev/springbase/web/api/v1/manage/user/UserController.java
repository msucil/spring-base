package com.msucil.dev.springbase.web.api.v1.manage.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.msucil.dev.springbase.core.web.ResponseDetail;
import com.msucil.dev.springbase.domain.manage.user.User;
import com.msucil.dev.springbase.domain.manage.user.UserCommandService;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;

@RestController
@RequestMapping("/api/v1/manage/users")
public class UserController {

    private final UserQueryService queryService;
    private final UserCommandService commandService;

    public UserController(UserQueryService queryService, UserCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping
    public ResponseEntity<ResponseDetail> index() {
        return ResponseEntity.ok(
            ResponseDetail.builder().status(HttpStatus.OK.value()).detail(queryService.findAll())
                .build());
    }

    @PostMapping
    public ResponseEntity<ResponseDetail> save(@Valid @RequestBody User user) {
        return ResponseEntity.ok(
            ResponseDetail.builder().status(HttpStatus.OK.value()).detail(commandService.save(user))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDetail> update(@PathVariable Long id,
        @Valid @RequestBody User user) {
        final var data = queryService.findById(id);

        if (data.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Record Not Found");
        }

        return ResponseEntity.ok(ResponseDetail.builder()
            .status(HttpStatus.OK.value())
            .detail(commandService.update(user))
            .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDetail> delete(@PathVariable Long id) {
        commandService.delete(id);
        return ResponseEntity.ok(ResponseDetail.builder()
            .status(HttpStatus.OK.value())
            .detail("User Deleted Successfully")
            .build()
        );
    }
}

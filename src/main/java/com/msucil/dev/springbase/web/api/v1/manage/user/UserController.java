package com.msucil.dev.springbase.web.api.v1.manage.user;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msucil.dev.springbase.domain.manage.user.User;
import com.msucil.dev.springbase.domain.manage.user.UserCommandService;
import com.msucil.dev.springbase.domain.manage.user.UserQueryService;

@RestController
@RequestMapping("/api/v1/manage/users")
public class UserController {

    private final UserQueryService queryService;
    private final UserCommandService commandService;

    public UserController(
        UserQueryService queryService,
        UserCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping
    public ResponseEntity<List<User>> index() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid User user) {
        return ResponseEntity.ok(commandService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid User user) {
        return ResponseEntity.ok(commandService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        commandService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}

package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.dto.UserCreateRequest;
import com.nhnacademy.accountapi.dto.UserIdsGetDto;
import com.nhnacademy.accountapi.dto.UserRoleUpdateRequest;
import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody UserCreateRequest userCreateRequest) {
        if (userCreateRequest.email() == null || userCreateRequest.password() == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setEmail(userCreateRequest.email());
        user.setPassword(userCreateRequest.password());
        user.setStatus(User.Status.ACTIVE); // 기본값으로 ACTIVE 설정
        user.setRole(User.Role.MEMBER); // 기본값으로 USER 설정
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/roles")
    public ResponseEntity<Void> updateUserRole(@RequestBody UserRoleUpdateRequest request) {
        if (request.emails() == null || request.emails().isEmpty() || request.role() == null) {
            return ResponseEntity.badRequest().build();
        }

        userService.updateUserRoles(request.emails(), request.role());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ids")
    public ResponseEntity<List<Long>> getUserIdsInEmails(@RequestBody UserIdsGetDto emails) {
        if (emails == null || emails.emails() == null || emails.emails().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Long> userIds = userService.findUserIdsByEmails(emails.emails());
        if (userIds == null || userIds.isEmpty()) {
            ResponseEntity.noContent();
        }
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("/emails")
    public ResponseEntity<Map<Long, String>> getUserEmailsInIds(@RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Map<Long, String> userEmails = userService.findEmailsByIds(ids);
        if (userEmails == null || userEmails.isEmpty()) {
            ResponseEntity.noContent();
        }
        return ResponseEntity.ok(userEmails);
    }
}

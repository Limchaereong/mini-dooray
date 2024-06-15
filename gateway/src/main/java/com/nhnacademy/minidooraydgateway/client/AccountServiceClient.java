package com.nhnacademy.minidooraydgateway.client;

import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.UserCreateRequest;
import com.nhnacademy.minidooraydgateway.dto.UserIdsGetDto;
import com.nhnacademy.minidooraydgateway.dto.UserRoleUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "accountService", url = "${api.account-service.base-url}")
public interface AccountServiceClient {

//    @GetMapping("/users")
//    ResponseEntity<Page<User>> getAllUsers(Pageable pageable);

    @PostMapping("/users")
    ResponseEntity<Void> saveUser(@RequestBody UserCreateRequest user);

    @GetMapping("/users")
    ResponseEntity<User> getUserByEmail(@RequestParam String email);        // -> 로그인 할때 사용

    @GetMapping("/users/{userId}")
    ResponseEntity<User> getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/users/roles")
    ResponseEntity<Void> updateUserRole(@RequestBody UserRoleUpdateRequest request);  // -> 프로젝트 생성 or 멤버 추가할때

    @GetMapping("/users/ids")
    ResponseEntity<List<Long>> getUserIdsInEmails(@RequestBody UserIdsGetDto emails);     // 멤버 추가할때 ProjectAPI 에 넘기는 용

    @GetMapping("/users/emails")
    ResponseEntity<Map<Long, String>> getUserEmailsInIds(@RequestParam List<Long> ids);
}
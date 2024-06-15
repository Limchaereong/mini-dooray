package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.AccountServiceClient;
import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountServiceClient accountServiceClient;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserCreateRequest userCreateRequest) {
        UserCreateRequest encoded = UserCreateRequest.builder()
                .email(userCreateRequest.email())
                .password(passwordEncoder.encode(userCreateRequest.password()))
                .build();

        ResponseEntity<Void> response = accountServiceClient.saveUser(encoded);
        if (response.getStatusCode() != HttpStatus.CREATED) {
            if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 사용자 데이터입니다.");
            } else if (response.getStatusCode() == HttpStatus.CONFLICT) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "사용자가 이미 존재합니다.");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 오류가 발생했습니다.");
            }
        }

    }

    public User getUserById(Long userId) {
        ResponseEntity<User> response = accountServiceClient.getUserById(userId);
        return response.getBody();
    }

    public User getUserByEmail(String email) {
        ResponseEntity<User> response = accountServiceClient.getUserByEmail(email);
        return response.getBody();
    }

    public void updateUserRole(List<String> userEmails, String role) {
        UserRoleUpdateRequest request = UserRoleUpdateRequest.builder()
                .emails(userEmails)
                .role(role)
                .build();
        ResponseEntity<Void> response = accountServiceClient.updateUserRole(request);
    }

    public List<Long> getUserIdsByEmails(List<String> emails) {
        ResponseEntity<List<Long>> response = accountServiceClient.getUserIdsInEmails(UserIdsGetDto.builder().emails(emails).build());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ResponseStatusException(response.getStatusCode(), "해당 이메일을 찾을 수 없습니다.");
    }

    public List<ProjectWithAdminEmailDto>  findEmailsByIds(Page<ProjectGetDto> projects, List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return projects.stream()
                    .map(project -> {
                        return ProjectWithAdminEmailDto.builder()
                                .id(project.id())
                                .name(project.name())
                                .status(project.status())
                                .build();
                    })
                    .toList();
        }

        ResponseEntity<Map<Long, String>> response = accountServiceClient.getUserEmailsInIds(ids);
        Map<Long, String> emails = response.getBody();
        return projects.stream()
                .map(project -> {
                    String adminEmail = emails != null ? emails.get(project.adminUserId()) : null;
                    return ProjectWithAdminEmailDto.builder()
                            .id(project.id())
                            .name(project.name())
                            .status(project.status())
                            .adminEmail(adminEmail)
                            .build();
                })
                .toList();
    }


}

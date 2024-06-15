package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.AccountServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AccountServiceClient accountServiceClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "password", User.Status.ACTIVE);
    }

    @Test
    void testSaveUser_Success() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountServiceClient.saveUser(any(UserCreateRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        assertDoesNotThrow(() -> userService.saveUser(userCreateRequest));
        verify(accountServiceClient, times(1)).saveUser(any(UserCreateRequest.class));
    }

    @Test
    void testSaveUser_BadRequest() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountServiceClient.saveUser(any(UserCreateRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.saveUser(userCreateRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void testSaveUser_Conflict() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountServiceClient.saveUser(any(UserCreateRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONFLICT));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.saveUser(userCreateRequest));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    void testSaveUser_InternalServerError() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(accountServiceClient.saveUser(any(UserCreateRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.saveUser(userCreateRequest));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    @Test
    void testGetUserById() {
        when(accountServiceClient.getUserById(anyLong())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getStatus(), foundUser.getStatus());
    }

    @Test
    void testGetUserByEmail() {
        when(accountServiceClient.getUserByEmail(anyString())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        User foundUser = userService.getUserByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getStatus(), foundUser.getStatus());
    }

    @Test
    void testUpdateUserRole() {
        UserRoleUpdateRequest request = UserRoleUpdateRequest.builder()
                .emails(List.of("test@example.com"))
                .role("PROJECT_ADMIN")
                .build();

        when(accountServiceClient.updateUserRole(any(UserRoleUpdateRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        assertDoesNotThrow(() -> userService.updateUserRole(List.of("test@example.com"), "PROJECT_ADMIN"));
        verify(accountServiceClient, times(1)).updateUserRole(any(UserRoleUpdateRequest.class));
    }


    @Test
    void testGetUserIdsByEmails_Success() {
        List<Long> ids = List.of(1L, 2L, 3L);
        when(accountServiceClient.getUserIdsInEmails(any(UserIdsGetDto.class)))
                .thenReturn(new ResponseEntity<>(ids, HttpStatus.OK));

        List<Long> foundIds = userService.getUserIdsByEmails(List.of("test@example.com"));

        assertNotNull(foundIds);
        assertEquals(ids.size(), foundIds.size());
        assertEquals(ids, foundIds);
    }

    @Test
    void testGetUserIdsByEmails_NotFound() {
        when(accountServiceClient.getUserIdsInEmails(any(UserIdsGetDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.getUserIdsByEmails(List.of("test@example.com")));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void testFindEmailsByIds() {
        List<Long> ids = List.of(1L);
        List<ProjectGetDto> projectDtos = List.of(new ProjectGetDto(1L, "Project1", Project.Status.ACTIVE, 1L));
        Page<ProjectGetDto> projects = new PageImpl<>(projectDtos);

        when(accountServiceClient.getUserEmailsInIds(anyList()))
                .thenReturn(new ResponseEntity<>(Map.of(1L, "test@example.com"), HttpStatus.OK));

        List<ProjectWithAdminEmailDto> result = userService.findEmailsByIds(projects, ids);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).adminEmail());
    }
}

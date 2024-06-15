package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.dto.UserCreateRequest;
import com.nhnacademy.accountapi.dto.UserIdsGetDto;
import com.nhnacademy.accountapi.dto.UserRoleUpdateRequest;
import com.nhnacademy.accountapi.entity.User;
import com.nhnacademy.accountapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSaveUser_Success() {
        UserCreateRequest request = new UserCreateRequest("test@example.com", "password");
        when(userService.saveUser(any(User.class))).thenReturn(null); // saveUser가 반환하는 타입에 맞게 수정

        ResponseEntity<Void> response = userController.saveUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    public void testSaveUser_BadRequest() {
        UserCreateRequest request = new UserCreateRequest(null, "password");

        ResponseEntity<Void> response = userController.saveUser(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    public void testGetUserById_Success() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserByEmail_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserByEmail("test@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserByEmail_NotFound() {
        when(userService.findByEmail("test@example.com")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserByEmail("test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserRole_Success() {
        UserRoleUpdateRequest request = new UserRoleUpdateRequest(Arrays.asList("test@example.com"), "PROJECT_ADMIN");
        doNothing().when(userService).updateUserRoles(request.emails(), request.role());

        ResponseEntity<Void> response = userController.updateUserRole(request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).updateUserRoles(request.emails(), request.role());
    }

    @Test
    public void testUpdateUserRole_BadRequest() {
        UserRoleUpdateRequest request = new UserRoleUpdateRequest(null, "PROJECT_ADMIN");

        ResponseEntity<Void> response = userController.updateUserRole(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).updateUserRoles(anyList(), any());
    }

    @Test
    public void testGetUserIdsInEmails_Success() {
        UserIdsGetDto emails = new UserIdsGetDto(Arrays.asList("test@example.com"));
        List<Long> ids = Arrays.asList(1L, 2L);
        when(userService.findUserIdsByEmails(emails.emails())).thenReturn(ids);

        ResponseEntity<List<Long>> response = userController.getUserIdsInEmails(emails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ids, response.getBody());
    }

    @Test
    public void testGetUserIdsInEmails_BadRequest() {
        UserIdsGetDto emails = new UserIdsGetDto(null);

        ResponseEntity<List<Long>> response = userController.getUserIdsInEmails(emails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

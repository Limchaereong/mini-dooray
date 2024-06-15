package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.dto.UserCreateRequest;
import com.nhnacademy.minidooraydgateway.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AccountControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/signup"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    public void testSignupSubmit_Success() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        mockMvc.perform(post("/signup")
                        .flashAttr("userCreateRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));

        verify(userService).saveUser(any(UserCreateRequest.class));
    }

    @Test
    public void testSignupSubmit_ValidationError() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .email("invalid-email")
                .password("")
                .build();

        mockMvc.perform(post("/signup")
                        .flashAttr("userCreateRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("account/signup"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testSignupSubmit_ResponseStatusException() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 사용자 데이터입니다."))
                .when(userService).saveUser(any(UserCreateRequest.class));

        mockMvc.perform(post("/signup")
                        .flashAttr("userCreateRequest", request))
                .andExpect(status().isOk())
                .andExpect(view().name("account/signup"))
                .andExpect(model().attributeExists("error"));
    }
}

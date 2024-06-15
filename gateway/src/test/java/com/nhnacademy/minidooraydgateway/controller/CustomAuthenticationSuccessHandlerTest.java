package com.nhnacademy.minidooraydgateway.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class CustomAuthenticationSuccessHandlerTest {

    private CustomAuthenticationSuccessHandler successHandler;

    @BeforeEach
    public void setUp() {
        successHandler = new CustomAuthenticationSuccessHandler();
    }

    @Test
    public void testOnAuthenticationSuccess() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("user");

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response, times(1)).sendRedirect("/projects");
        verify(authentication, times(1)).getName();
        verifyNoMoreInteractions(response, request, authentication);
    }
}

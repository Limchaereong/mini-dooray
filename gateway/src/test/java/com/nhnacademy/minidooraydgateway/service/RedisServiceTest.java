package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.auth.CustomUserDetails;
import com.nhnacademy.minidooraydgateway.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private SessionRepository<Session> sessionRepository;

    @Mock
    private Session session;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RedisService redisService;

    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        User user = new User(1L, "test@example.com", "password", User.Status.ACTIVE);
        customUserDetails = new CustomUserDetails(user, Collections.emptyList());
    }

    @Test
    void testGetUserDetailsFromSession_SessionNotFound() {
        when(sessionRepository.findById(anyString())).thenReturn(null);

        Optional<CustomUserDetails> result = redisService.getUserDetailsFromSession("session-id");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailsFromSession_SecurityContextNotFound() {
        when(sessionRepository.findById(anyString())).thenReturn(session);
        when(session.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(null);

        Optional<CustomUserDetails> result = redisService.getUserDetailsFromSession("session-id");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailsFromSession_UserDetailsNotFound() {
        when(sessionRepository.findById(anyString())).thenReturn(session);
        when(session.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(null);

        Optional<CustomUserDetails> result = redisService.getUserDetailsFromSession("session-id");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetUserDetailsFromSession_Success() {
        when(sessionRepository.findById(anyString())).thenReturn(session);
        when(session.getAttribute("SPRING_SECURITY_CONTEXT")).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);

        Optional<CustomUserDetails> result = redisService.getUserDetailsFromSession("session-id");

        assertTrue(result.isPresent());
        assertEquals(customUserDetails, result.get());
    }

    // CustomAuthenticationToken 클래스를 사용해 사용자 인증을 모의합니다.
    private static class CustomAuthenticationToken extends org.springframework.security.authentication.UsernamePasswordAuthenticationToken {
        public CustomAuthenticationToken(CustomUserDetails principal) {
            super(principal, principal.getPassword(), principal.getAuthorities());
        }
    }
}

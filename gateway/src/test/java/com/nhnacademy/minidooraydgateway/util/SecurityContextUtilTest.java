package com.nhnacademy.minidooraydgateway.util;

import com.nhnacademy.minidooraydgateway.auth.CustomUserDetails;
import com.nhnacademy.minidooraydgateway.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.springframework.security.core.context.SecurityContextImpl;

public class SecurityContextUtilTest {

    @Mock
    private RedisService redisService;

    @Mock
    private Authentication authentication;

    @Spy
    @InjectMocks
    private SecurityContextUtil securityContextUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Security Context에서 Authentication 가져오기")
    public void testGetAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<Authentication> result = securityContextUtil.getAuthentication();

        assertTrue(result.isPresent());
        assertEquals(authentication, result.get());
    }

    @Test
    @DisplayName("Security Context에서 현재 사용자 정보 가져오기")
    public void testGetCurrentUserDetailsFromSecurityContext() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<CustomUserDetails> result = securityContextUtil.getCurrentUserDetails();

        assertTrue(result.isPresent());
        assertEquals(userDetails, result.get());
    }

    @Test
    @DisplayName("Security Context가 없을 때 Redis에서 현재 사용자 정보 가져오기")
    public void testGetCurrentUserDetailsFromRedis() {
        SecurityContextHolder.getContext().setAuthentication(null); // Clear authentication to simulate Redis usage

        String sessionId = "testSessionId";
        CustomUserDetails userDetails = mock(CustomUserDetails.class);

        when(redisService.getUserDetailsFromSession(sessionId)).thenReturn(Optional.of(userDetails));
        doReturn(sessionId).when(securityContextUtil).getSessionIdFromSecurityContext();

        Optional<CustomUserDetails> result = securityContextUtil.getCurrentUserDetails();

        assertTrue(result.isPresent());
        assertEquals(userDetails, result.get());
    }

    @Test
    @DisplayName("Security Context에서 Authentication의 Principal이 CustomUserDetails가 아닌 경우 NullPointerException 발생")
    public void testGetCurrentUserDetailsWhenPrincipalIsNotCustomUserDetails() {
        String principal = "NotCustomUserDetails";
        when(authentication.getPrincipal()).thenReturn(principal);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        doThrow(new NullPointerException()).when(authentication).getPrincipal();

        assertThrows(NullPointerException.class, () -> securityContextUtil.getCurrentUserDetails());
    }

    @Test
    @DisplayName("현재 사용자 ID 가져오기")
    public void testGetCurrentUserId() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<Long> result = securityContextUtil.getCurrentUserId();

        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }

    @Test
    @DisplayName("현재 사용자 이메일 가져오기")
    public void testGetCurrentUserEmail() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<String> result = securityContextUtil.getCurrentUserEmail();

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get());
    }

    @Test
    @DisplayName("현재 사용자가 특정 권한을 가지고 있는지 확인")
    public void testHasAuthority() {
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getAuthorities()).thenReturn(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean result = securityContextUtil.hasAuthority("ROLE_USER");

        assertTrue(result);
    }

    @Test
    @DisplayName("세션 ID가 있을 때 Redis에서 사용자 정보 가져오기")
    public void testGetUserDetailsFromRedisWithSessionId() {
        SecurityContextHolder.getContext().setAuthentication(null); // Clear authentication

        String sessionId = "testSessionId";
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(redisService.getUserDetailsFromSession(sessionId)).thenReturn(Optional.of(userDetails));
        doReturn(sessionId).when(securityContextUtil).getSessionIdFromSecurityContext();

        Optional<CustomUserDetails> result = securityContextUtil.getUserDetailsFromRedis();

        assertTrue(result.isPresent());
        assertEquals(userDetails, result.get());
    }

    @Test
    @DisplayName("세션 ID가 없을 때 Redis에서 사용자 정보 가져오기")
    public void testGetUserDetailsFromRedisWithoutSessionId() {
        doReturn(null).when(securityContextUtil).getSessionIdFromSecurityContext();

        Optional<CustomUserDetails> result = securityContextUtil.getUserDetailsFromRedis();

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Security Context에서 세션 ID 가져오기")
    public void testGetSessionIdFromSecurityContext() {
        when(authentication.getDetails()).thenReturn("testSessionId");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String sessionId = securityContextUtil.getSessionIdFromSecurityContext();

        assertEquals("testSessionId", sessionId);
    }

    @Test
    @DisplayName("Security Context에서 세션 ID 가져올 때 Authentication이 null인 경우")
    public void testGetSessionIdFromSecurityContextWhenAuthenticationIsNull() {
        SecurityContextHolder.getContext().setAuthentication(null);

        String sessionId = securityContextUtil.getSessionIdFromSecurityContext();

        assertNull(sessionId);
    }
}
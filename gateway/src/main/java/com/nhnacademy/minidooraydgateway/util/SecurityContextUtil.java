package com.nhnacademy.minidooraydgateway.util;


import com.nhnacademy.minidooraydgateway.auth.CustomUserDetails;
import com.nhnacademy.minidooraydgateway.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class SecurityContextUtil {

    private final RedisService redisService;

    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public Optional<CustomUserDetails> getCurrentUserDetails() {
        return getAuthentication()
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof CustomUserDetails) {
                        return (CustomUserDetails) authentication.getPrincipal();
                    }
                    return null;
                })
                .or(this::getUserDetailsFromRedis);
    }

    public Optional<Long> getCurrentUserId() {
        return getCurrentUserDetails()
                .map(CustomUserDetails::getId);
    }

    public Optional<String> getCurrentUserEmail() {
        return getCurrentUserDetails()
                .map(CustomUserDetails::getUsername);
    }

    public boolean hasAuthority(String authority) {
        return getCurrentUserDetails()
                .map(userDetails -> userDetails.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals(authority)))
                .orElse(false);
    }


    Optional<CustomUserDetails> getUserDetailsFromRedis() {
        String sessionId = getSessionIdFromSecurityContext();
        return sessionId != null ? redisService.getUserDetailsFromSession(sessionId) : Optional.empty();
    }

    String getSessionIdFromSecurityContext() {
        return getAuthentication()
                .map(authentication -> authentication.getDetails().toString())
                .orElse(null);
    }
}
package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RedisService {

    private final SessionRepository<?> sessionRepository;

    public Optional<CustomUserDetails> getUserDetailsFromSession(String sessionId) {
        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            return Optional.empty();
        }

        SecurityContext securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContext == null || securityContext.getAuthentication() == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((CustomUserDetails) securityContext.getAuthentication().getPrincipal());
    }
}
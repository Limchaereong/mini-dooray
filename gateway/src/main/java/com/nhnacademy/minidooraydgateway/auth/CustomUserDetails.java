package com.nhnacademy.minidooraydgateway.auth;

import com.nhnacademy.minidooraydgateway.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;
    private final User.Status status;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.id = user.getId();
        this.status = user.getStatus();
    }

    public Long getId() {
        return id;
    }

    public User.Status getStatus() {
        return status;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != User.Status.ENDED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != User.Status.DORMANT;
    }

    @Override
    public boolean isEnabled() {
        return status == User.Status.ACTIVE;
    }
}

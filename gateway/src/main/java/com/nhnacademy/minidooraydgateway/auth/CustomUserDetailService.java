package com.nhnacademy.minidooraydgateway.auth;

import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.exception.NonActiveMemberException;
import com.nhnacademy.minidooraydgateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("해당 아이디는 찾을 수 없습니다.");
        }
        if (user.getStatus() != User.Status.ACTIVE) {
            throw new NonActiveMemberException();
        }
        return new CustomUserDetails(
                user,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}

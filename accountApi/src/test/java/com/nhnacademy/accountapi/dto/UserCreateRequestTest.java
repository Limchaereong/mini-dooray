package com.nhnacademy.accountapi.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateRequestTest {

    @Test
    void testUserCreateRequestBuilder() {
        // given
        String email = "test@example.com";
        String password = "password123";

        // when
        UserCreateRequest request = UserCreateRequest.builder()
                .email(email)
                .password(password)
                .build();

        // then
        assertThat(request.email()).isEqualTo(email);
        assertThat(request.password()).isEqualTo(password);
    }

    @Test
    void testUserCreateRequestConstructor() {
        // given
        String email = "test@example.com";
        String password = "password123";

        // when
        UserCreateRequest request = new UserCreateRequest(email, password);

        // then
        assertThat(request.email()).isEqualTo(email);
        assertThat(request.password()).isEqualTo(password);
    }
}
